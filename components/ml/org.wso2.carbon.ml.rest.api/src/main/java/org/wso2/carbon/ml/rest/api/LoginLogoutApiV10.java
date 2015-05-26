/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.ml.rest.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.wso2.carbon.context.PrivilegedCarbonContext;

/**
 * WSO2 ML Analyses API. All the operations related to Analyses are delegated from this class.
 */
public class LoginLogoutApiV10 extends MLRestAPI {

    private static final Log logger = LogFactory.getLog(LoginLogoutApiV10.class);
    
    @Context
    HttpServletRequest httpServletRequest;

    public LoginLogoutApiV10() {
    }

    /**
     * HTTP Options method implementation for analysis API.
     * 
     * @return
     */
    @OPTIONS
    public Response options() {
        return Response.ok().header(HttpHeaders.ALLOW, "POST").build();
    }

    /**
     * Login
     */
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Login() {
        //create session if not found
        HttpSession httpSession = httpServletRequest.getSession();
        PrivilegedCarbonContext carbonContext = PrivilegedCarbonContext.getThreadLocalCarbonContext();
        httpSession.setAttribute("userName", carbonContext.getUsername());
        httpSession.setAttribute("tenantDomain", carbonContext.getTenantDomain());
        httpSession.setAttribute("tenantId", carbonContext.getTenantId());
        //String sessionId = httpSession.getId();
        return Response.status(Response.Status.OK).entity("User loged in: " + carbonContext.getUsername()).build();
    }

    /**
     * Logout.
     */
    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Logout() {
        HttpSession session = httpServletRequest.getSession();
        if(session != null){
            session.invalidate();
        }
        return Response.status(Response.Status.OK).entity("User loged out.").build();
    }
}