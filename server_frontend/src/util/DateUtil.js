/*
日期工具
 */

export function dateFormat(date) {
    if (date != null) {
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        // const hh = dateMat.getHours();
        // const mm = dateMat.getMinutes();
        // const ss = dateMat.getSeconds();
        return year + "-" + month + "-" + day;
    }
}