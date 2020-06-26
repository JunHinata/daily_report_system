package models.validators;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import models.Report;

public class ReportValidator {
    public static List<String> validate(Report r) {
        List<String> errors = new ArrayList<String>();

        String title_error = _validateTitle(r.getTitle());
        if(!title_error.equals("")) {
            errors.add(title_error);
        }

        String content_error = _validateContent(r.getContent());
        if(!content_error.equals("")) {
            errors.add(content_error);
        }

        String punch_in_error = _validatePunch_in(r.getPunch_in());
        if(!punch_in_error.equals("")) {
            errors.add(punch_in_error);
        }

        String punch_out_error = _validatePunch_out(r.getPunch_out());
        if(!punch_out_error.equals("")) {
            errors.add(punch_out_error);
        }

        // 出勤時刻と退勤時刻が入力されていたら、退勤時刻が出勤時刻より後になっているかチェック
        if(punch_in_error.equals("") && punch_out_error.equals("")) {
            String punch_error = _validatePunch(r.getPunch_in(), r.getPunch_out());
            if(!punch_error.equals("")) {
                errors.add(punch_error);
            }
        }

        return errors;
    }

    private static String _validateTitle(String title) {
        if(title == null || title.equals("")) {
            return "タイトルを入力してください。";
        }

        return "";
    }

    private static String _validateContent(String content) {
        if(content == null || content.equals("")) {
            return "内容を入力してください。";
        }

        return "";
    }

    private static String _validatePunch_in(Time punch_in) {
        if(punch_in == null || punch_in.equals("")) {
            return "出勤時刻を入力してください。";
        }

        return "";
    }

    private static String _validatePunch_out(Time punch_out) {
        if(punch_out == null || punch_out.equals("")) {
            return "退勤時刻を入力してください。";
        }

        return "";
    }

    private static String _validatePunch(Time punch_in, Time punch_out) {
        if(punch_in.toLocalTime().isAfter(punch_out.toLocalTime())) {
           return "退勤時刻が出勤時刻より前になっています。訂正してください。";
        }

       return "";
    }

}
