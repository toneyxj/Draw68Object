package com.moxi.writeNote;

import com.moxi.handwritinglibs.db.index.IndexDbUtils;
import com.moxi.writeNote.utils.UserInformation;
import com.mx.mxbase.base.MyApplication;
import com.mx.mxbase.constant.APPLog;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2017/2/16.
 */
public class WriteNoteApplication extends MyApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "a5ffb16b07", APPLog.is);
        IndexDbUtils.getInstance().initDb(getApplicationContext());
        UserInformation.getInstance().initUserInfor(getInstance());
    }
}
