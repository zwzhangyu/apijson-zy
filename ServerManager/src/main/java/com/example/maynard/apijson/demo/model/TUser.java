package com.example.maynard.apijson.demo.model;

import apijson.MethodAccess;

import static apijson.RequestRole.*;
import static apijson.RequestRole.LOGIN;

@MethodAccess(
        GET = {OWNER, ADMIN, UNKNOWN, LOGIN},
        GETS = {OWNER, ADMIN, UNKNOWN, LOGIN},
        POST = {OWNER, ADMIN, UNKNOWN, LOGIN},
        PUT = {OWNER, ADMIN, UNKNOWN, LOGIN},
        DELETE = {OWNER, ADMIN, UNKNOWN, LOGIN}
)
public class TUser {
}
