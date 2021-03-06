package com.xt.cfp.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xt.cfp.core.pojo.UserInfo;
import com.xt.cfp.core.pojo.UserMessage;
import com.xt.cfp.core.service.UserMessageService;
import com.xt.cfp.core.util.SecurityUtil;



@Controller
@RequestMapping(value = "/message")
public class UserMesageController extends BaseController{
	@Autowired
	UserMessageService userMessageService;
	
    @RequestMapping(value = "/toUserMessage")
    public String toUserMessage(){
    	
        return "/person/userMessage";
    }
    @RequestMapping(value = "/userMessageList")
	@ResponseBody
    public Object userMessageList(HttpServletRequest request, HttpSession session,
    		@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
    		@RequestParam(value = "messageType[]", required=false) String[] messageType,
    		@RequestParam(value = "status[]", required=false) String[] status) {
    	UserInfo currentUser = SecurityUtil.getCurrentUser(true);
    	System.out.println(pageSize+" "+pageNo+" "+messageType.length+" "+status.length);
		return userMessageService.receptionUserMessageList(pageNo, pageSize, currentUser.getUserId(), status, messageType, null); 
    }
    @RequestMapping(value = "/readMessage")
   	@ResponseBody
       public Object readMessage(HttpServletRequest request, HttpSession session, String reciveId,
       		@RequestParam(value = "msgId", required=false) Long msgId) {
       	UserInfo currentUser = SecurityUtil.getCurrentUser(true);
       	Long reciveIdL = null ;
       	if(StringUtils.isBlank(reciveId)){
       		reciveId = null ;
       	}else{
       		reciveIdL = Long.valueOf(reciveId);
       	}
       	UserMessage userMessage = userMessageService.getReadMessage(reciveIdL, currentUser.getUserId(),msgId);
       	
       	return userMessage;
    }
    @RequestMapping(value = "/userMessageCount")
	@ResponseBody
    public Object userMessageCount(HttpServletRequest request, HttpSession session,
    		@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
    		@RequestParam(value = "d_count[]", required=false) String[] messageType,
    		@RequestParam(value = "w_count[]", required=false) String[] status) {
    	UserInfo currentUser = SecurityUtil.getCurrentUser(true);
    	System.out.println(messageType.length+" "+status.length);
		return userMessageService.receptionUserMessageList(pageNo, pageSize, currentUser.getUserId(), status, messageType, null).getTotal(); 
    }
}
