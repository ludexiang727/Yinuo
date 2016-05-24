package com.yinuo.mode;

import com.yinuo.base.BaseObject;
import com.yinuo.utils.PreferenceUtils;

/**
 * Created by ludexiang on 2016/5/24.
 */
public class UserModel extends BaseObject {
    private int mUserId;
    private String mUserHeader;
    private String mUserNickName;
    private String mUserAccount;
    // user qrcode url
    private String mUserQrcode;
    private int mUserGender;
    // user credit report url
    private String mUserCreditReport;
    // user loan process url
    private String mUserLoanProcessing;
    // user loan repayment url
    private String mUserLoanRepayment;
    /** register location - 户口所在地 */
    private String mUserRegisterLocation;
    private String mUserSchool;
    private String mUserHighestEdu;
    private String mUserEduMajor;
    private String mUserWorkIn;
    private String mUserJob;
    private String mUserIdCard;

    public String getUserAccount() {
        return mUserAccount;
    }

    public void setUserAccount(String userAccount) {
        this.mUserAccount = userAccount;
        PreferenceUtils.getInstance().setAccount(userAccount);
    }

    public String getUserCreditReport() {
        return mUserCreditReport;
    }

    public void setUserCreditReport(String userCreditReport) {
        this.mUserCreditReport = userCreditReport;
    }

    public String getUserEduMajor() {
        return mUserEduMajor;
    }

    public void setUserEduMajor(String userEduMajor) {
        this.mUserEduMajor = userEduMajor;
    }

    public int getUserGender() {
        return mUserGender;
    }

    public void setUserGender(int userGender) {
        this.mUserGender = userGender;
    }

    public String getUserHeader() {
        return mUserHeader;
    }

    public void setUserHeader(String userHeader) {
        this.mUserHeader = userHeader;
    }

    public String getUserHighestEdu() {
        return mUserHighestEdu;
    }

    public void setUserHighestEdu(String userHighestEdu) {
        this.mUserHighestEdu = userHighestEdu;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public String getUserIdCard() {
        return mUserIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.mUserIdCard = userIdCard;
    }

    public String getUserJob() {
        return mUserJob;
    }

    public void setUserJob(String userJob) {
        this.mUserJob = userJob;
    }

    public String getUserLoanProcessing() {
        return mUserLoanProcessing;
    }

    public void setUserLoanProcessing(String userLoanProcessing) {
        this.mUserLoanProcessing = userLoanProcessing;
    }

    public String getUserLoanRepayment() {
        return mUserLoanRepayment;
    }

    public void setUserLoanRepayment(String userLoanRepayment) {
        this.mUserLoanRepayment = userLoanRepayment;
    }

    public String getUserNickName() {
        return mUserNickName;
    }

    public void setUserNickName(String userNickName) {
        this.mUserNickName = userNickName;
    }

    public String getUserQrcode() {
        return mUserQrcode;
    }

    public void setUserQrcode(String userQrcode) {
        this.mUserQrcode = userQrcode;
    }

    public String getUserRegisterLocation() {
        return mUserRegisterLocation;
    }

    public void setUserRegisterLocation(String userRegisterLocation) {
        this.mUserRegisterLocation = userRegisterLocation;
    }

    public String getUserSchool() {
        return mUserSchool;
    }

    public void setUserSchool(String userSchool) {
        this.mUserSchool = userSchool;
    }

    public String getUserWorkIn() {
        return mUserWorkIn;
    }

    public void setUserWorkIn(String userWorkIn) {
        this.mUserWorkIn = userWorkIn;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "mUserAccount='" + mUserAccount + '\'' +
                ", mUserId=" + mUserId +
                ", mUserHeader='" + mUserHeader + '\'' +
                ", mUserNickName='" + mUserNickName + '\'' +
                ", mUserQrcode='" + mUserQrcode + '\'' +
                ", mUserGender=" + mUserGender +
                ", mUserCreditReport='" + mUserCreditReport + '\'' +
                ", mUserLoanProcessing='" + mUserLoanProcessing + '\'' +
                ", mUserLoanRepayment='" + mUserLoanRepayment + '\'' +
                ", mUserRegisterLocation='" + mUserRegisterLocation + '\'' +
                ", mUserSchool='" + mUserSchool + '\'' +
                ", mUserHighestEdu='" + mUserHighestEdu + '\'' +
                ", mUserEduMajor='" + mUserEduMajor + '\'' +
                ", mUserWorkIn='" + mUserWorkIn + '\'' +
                ", mUserJob='" + mUserJob + '\'' +
                ", mUserIdCard='" + mUserIdCard + '\'' +
                '}';
    }
}
