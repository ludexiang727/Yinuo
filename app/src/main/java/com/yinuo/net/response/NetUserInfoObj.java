package com.yinuo.net.response;

import com.yinuo.base.BaseApplication;
import com.yinuo.mode.UserModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;
import com.yinuo.utils.MessageEventUtil;

import org.json.JSONObject;

/**
 * Created by ludexiang on 2016/5/24.
 */
public class NetUserInfoObj extends NetBaseObject {

    @Override
    protected void parse(JSONObject obj) {
        super.parse(obj);
        UserModel model = new UserModel();
        JSONObject user = NetParseUtils.getObject(NetConstant.NET_JSON_USER_INFOS, obj);
        int id = NetParseUtils.getInt(NetConstant.NET_JSON_USER_ID, user);
        String header = NetParseUtils.getString(NetConstant.NET_JSON_USER_HEADER, user);
        String nickName = NetParseUtils.getString(NetConstant.NET_JSON_USER_NICK_NAME, user);
        String account = NetParseUtils.getString(NetConstant.NET_JSON_USER_ACCOUNT, user);
        String qrcode = NetParseUtils.getString(NetConstant.NET_JSON_USER_QRCODE_URL, user);
        int gender = NetParseUtils.getInt(NetConstant.NET_JSON_USER_GENDER, user);
        String creditReport = NetParseUtils.getString(NetConstant.NET_JSON_USER_CREDIT_REPORT, user);
        String process = NetParseUtils.getString(NetConstant.NET_JSON_USER_LOAN_PROCESS, user);
        String repayment = NetParseUtils.getString(NetConstant.NET_JSON_USER_LOAN_REPAYMENT, user);

        String registerLoc = NetParseUtils.getString(NetConstant.NET_JSON_USER_REGISTER_LOCATION, user);
        String school = NetParseUtils.getString(NetConstant.NET_JSON_USER_SCHOOL, user);
        String highEdu = NetParseUtils.getString(NetConstant.NET_JSON_USER_HIGHEST_EDU, user);
        String major = NetParseUtils.getString(NetConstant.NET_JSON_USER_EDU_MAJOR, user);
        String workIn = NetParseUtils.getString(NetConstant.NET_JSON_USER_WORK_IN, user);
        String job = NetParseUtils.getString(NetConstant.NET_JSON_USER_JOB, user);
        String idCard = NetParseUtils.getString(NetConstant.NET_JSON_USER_ID_CARD, user);

        model.setUserId(id);
        model.setUserHeader(header);
        model.setUserNickName(nickName);
        model.setUserAccount(account);
        model.setUserQrcode(qrcode);
        model.setUserGender(gender);
        model.setUserCreditReport(creditReport);
        model.setUserLoanProcessing(process);
        model.setUserLoanRepayment(repayment);
        model.setUserRegisterLocation(registerLoc);
        model.setUserSchool(school);
        model.setUserHighestEdu(highEdu);
        model.setUserEduMajor(major);
        model.setUserWorkIn(workIn);
        model.setUserJob(job);
        model.setUserIdCard(idCard);
        BaseApplication.getInstance().setUserModel(model);
        MessageEventUtil.getInstance().post();
    }
}
