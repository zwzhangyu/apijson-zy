<template>
  <div class="main-login">
    <lyz-layout
        :pagination="pagination"
        :label="label"
        @handleSizeChange="handleSizeChange"
        @handleCurrentChange="handleCurrentChange">
      <div slot="banner" class="top-right message-nav">
        <el-button type="primary" size="small" @click="showCreateView" slot="reference">新增</el-button> &nbsp;
        <el-input placeholder="请输入搜索内容" v-model="queryKeyword" class="input-with-select" clearable>
          <el-button slot="append" icon="el-icon-search" @click="queryList"></el-button>
        </el-input>
      </div>
      <div slot="main" class="main-body">
        <el-table
            :data="tableData"
            stripe
            v-loading="loginLoading"
            tooltip-effect="light"
            height="100%"
            style="width: 100%"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column
              v-for="(data,index) in tableHeader"
              :show-overflow-tooltip="true"
              :key="index"
              :prop="data.prop"
              :label="data.label"
              :min-width="data['min-width']"
              :align="data.align">
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
              min-width="120">
            <template slot-scope="scope">
              <el-button type="text" size="mini"
                         @click="edit(scope.row)">编辑
              </el-button>
              <el-button type="text" size="mini" class="danger-text"
                         @click="deleteMessage(scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </lyz-layout>
    <!--  新增弹框  -->
    <el-dialog title='新增消息' :visible.sync="messageVisible" width="33%" center
               class="user-dialog">
      <el-form :model="messageForm" :label-width="messageLabelWidth" ref="messageForm"
               :validate-on-rule-change=false>
        <el-form-item label="状态" prop="serverName">
          <el-input v-model="mStatus"></el-input>
        </el-form-item>
        <el-form-item label="消息内容" prop="projectName">
          <el-input v-model="mContent" placeholder="请输入消息内容"></el-input>
        </el-form-item>

        <el-form-item label="用户" prop="projectName">
          <!--          <el-input v-model="mUser"></el-input>-->
          <el-select @change='handleChange' v-model="value" placeholder="请选择">
            <el-option
                v-for="item in userList"
                :key="item.TUser.user_id"
                :label="item.TUser.user_name"
                :value="item.TUser.user_name">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="messageVisible = false">取 消</el-button>
        <el-button type="primary" @click=saveMessage>确 定</el-button>
      </div>
    </el-dialog>
    <!--  编辑弹框  -->
    <el-dialog title='编辑消息' :visible.sync="messageEditVisible" width="33%" center
               class="user-dialog">
      <el-form :model="messageForm" :label-width="messageLabelWidth" ref="messageForm"
               :validate-on-rule-change=false>
        <el-form-item label="ID" prop="serverName">
          <el-input v-model="eId"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="serverName">
          <el-input v-model="eStatus"></el-input>
        </el-form-item>
        <el-form-item label="消息内容" prop="projectName">
          <el-input v-model="eContent" placeholder="请输入消息内容"></el-input>
        </el-form-item>
        <el-form-item label="用户" prop="projectName">
          <el-input v-model="eUser"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="messageEditVisible = false">取 消</el-button>
        <el-button type="primary" @click=editSubmit>更 新</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import lyzLayout from '@/components/lyzLayout';
import manage from '../manage.component';
import {debounce} from '../../../config/utils.js';
import axios from "axios";
import RequestUtil, {format} from "../../../util/RequestUtil";
import {dateFormat} from "../../../util/DateUtil";

let url_base = "http://127.0.0.1:8080"
let url_get = url_base + "/get"
const URL_POST = url_base + "/post";
const URL_PUT = url_base + "/put";
const URL_DELETE = url_base + "/delete";


let model = {
  msg: 'APIJSON',
  Moment: {
    content: 'content'
  },
  User: {
    name: 'name'
  },
  commentList: {}
}
export default {
  name: "manage",
  data() {
    return {
      //新增表单字段开始
      mStatus: "",
      mContent: "",
      mUser: "",
      //新增表单字段开始
      pagination: {
        pageIndex: 0,
        pageSize: 5,
        total: 0,
      },
      userList: {},
      value: "",
      labelId: '',
      //搜索参数
      queryKeyword: null,
      //编辑表单字段开始
      eId: "",
      eStatus: "",
      eContent: "",
      eUser: "",
      //编辑表单字段开始
      label: '消息管理',
      messageForm: {},
      messageVisible: false,
      messageEditVisible: false,
      messageLabelWidth: '90px',
      operate: '',
      tableData: [],
      loginLoading: false,
      tableHeader: [
        {
          prop: 'Message.id',
          label: 'ID',
          'min-width': 100,
          align: 'center',
        },
        {
          prop: 'Message.status',
          label: '状态',
          'min-width': 80,
          align: 'center',
        },
        {
          prop: 'Message.content',
          label: '消息内容',
          'min-width': 120,
          align: 'center',
        },
        {
          prop: 'TUser.user_name',
          label: '用户',
          'min-width': 100,
          align: 'center',
        },
        {
          prop: 'Message.create_time',
          label: '创建时间',
          'min-width': 120,
          align: 'center',
          formatter: "dateFormat"
        }
      ]
    };
  },
  components: {
    lyzLayout,
  },
  mixins: [manage],
  created() {
    this.queryList();
  },
  mounted() {
    //监听搜索框变化
    this.$watch('queryKeyword', debounce(() => {
      this.pagination.pageIndex = 1;
      this.queryList();
    }, 1000));
  },
  methods: {
    //列表查询
    queryList() {
      let this_ = this;
      //分页设置
      let page = this_.pagination.pageIndex - 1;
      page = page <= 0 ? 0 : page;
      let queryKeyword = this_.queryKeyword;
      //模糊搜索
      if (queryKeyword != null) {
        queryKeyword = '%' + queryKeyword + '%';
      }
      axios.post(url_get,
          {
            '[]': {
              'Message': {
                //指定查询字段
                "@column": "id,status,content,user_id,create_time",
                //排序
                "content$": queryKeyword,
                "@order": 'create_time-,id-'
              },
              "TUser": {
                //当前表的user_id关联Message.user_id
                "user_id@": "/Message/user_id",
                //查询字段
                "@column": "user_id,user_name"
              },
              //分页
              "page": page,
              "count": this_.pagination.pageSize,
              //查询全部总数
              "query": 2
            },
            //返回查询数
            "total@": "/[]/total"
          })
          .then(function (response) {
            this_.refresh(response.data)
            console.log(response.data)
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    //刷新列表数据
    refresh(data) {
      data = data || {};
      model.Moment = data.Moment || {};
      model.User = data.User || {};
      model.commentList = data['[]'] || [];
      this.tableData = model.commentList;
      console.log(this.tableData);
      this.pagination.total = data.total;
    },
    //删除数据
    deleteMessage(row) {
      let _this = this;
      let id = row.Message.id
      let eId = Number.parseInt(id);
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post(URL_DELETE, {
              "tag": "Message", "Message": {'id': eId}
            }
        ).then(function () {
          _this.$message({
            type: 'success',
            message: '删除成功'
          });
          _this.queryList();
        }).catch(function (error) {
          console.log(error);
        })
      }).catch(() => {
      });
    },
    //显示新增视图
    showCreateView(row) {
      console.log(row);
      this.messageVisible = true;
      this.operate = 'create';
      let _form = Object.assign({}, row);
      this.showUserSelct();
      this.messageForm = _form;
    },
    handleChange(val) {
      let obj = {}
      obj = this.userList.find((item) => {
        return item.TUser.user_name === val;
      });
      this.value = obj.TUser.user_name;
      this.labelId = obj.TUser.user_id;

    },
    //展示用户列表下拉列表
    showUserSelct() {
      let _this = this;
      axios.post(url_get, {
        '[]': {
          "TUser": {}
        }
      }).then(function (response) {
        console.log(response.data)
        _this.userList = response.data['[]'] || [];
      }).catch(function (error) {
        console.log(error);
      })
    },


    //新增Message
    saveMessage() {
      console.log('save');
      let createDate = dateFormat(new Date());
      let params = {
        'status': this.mStatus,
        'content': this.mContent,
        'user_id': this.labelId,
        'create_time': createDate
      };
      console.log(params)
      RequestUtil.postObject("Message", params);
      this.messageVisible = false;
      this.queryList();
    },
    //编辑
    edit(row) {
      let _this = this;
      let id = row.Message.id
      this.messageEditVisible = true;
      axios.post(url_get, {'Message': {'id': id}})
          .then(function (response) {
            let message = response.data.Message;
            _this.eId = message.id;
            _this.eStatus = message.status;
            _this.eContent = message.content;
            _this.eUser = message.user_id;
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    //更新数据
    editSubmit() {
      let _this = this;
      let eId = Number.parseInt(_this.eId);
      axios.post(URL_PUT, {
        "tag": "Message",
        "Message": {"id": eId, "status": _this.eStatus, "user_id": _this.eUser, "content": _this.eContent}
      }).then(function () {
        _this.$message({
          type: 'success',
          message: '更新成功'
        });
        _this.messageEditVisible = false;
        _this.queryList();
      }).catch(function (error) {
        console.log(error);
      })
    }
  }
}
</script>

<style scoped>
.main-login {
  height: 100%;
}

.danger-text {
  color: #F56C6C;
}

.message-nav {
  margin-right: 200px;
}
</style>