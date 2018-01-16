//package client.socket.li.com.sct_client;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Calendar;
//
//import rx.Observable;
//
///**
// * Created by TangWei on 2017/2/21.
// * 命令行工具类
// */
//
//public class CommandUtils {
//    /**
//     * 执行命令行，同步执行
//     *
//     * @param commandArray 命令行內容
//     * @return 命令行输出
//     */
//    public static Observable<String> execCommand(String[] commandArray) {
//        return Observable.create(subscriber -> {
//            Process exec = null;
//            try {
//                exec = Runtime.getRuntime().exec(commandArray);
//            } catch (IOException e) {
//                subscriber.onError(e);
//
//            }
//
//            BufferedReader bufferedReader;
//            //输出错误信息
//            if (exec != null) {
//                bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
//                String error = "time:" + getCurrentTime() + "_\n";
//                while (true) {
//                    String temp = null;
//                    try {
//                        temp = bufferedReader.readLine();
//                    } catch (IOException e) {
//                        subscriber.onError(e);
//                    }
//                    if (temp != null)
//                        error += temp + "\n";
//                    else {
//                        subscriber.onNext("\n**********************************************************************\n" + "error: " + error);
//                        break;
//                    }
//                }
//            }
//
//            //输出正常信息
//            if (exec != null) {
//                bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
//                String info = "time:" + getCurrentTime() + "_\n";
//                while (true) {
//                    String temp = null;
//                    try {
//                        temp = bufferedReader.readLine();
//                    } catch (IOException e) {
//                        subscriber.onError(e);
//                    }
//                    if (temp != null)
//                        info += temp + "\n";
//                    else {
//                        subscriber.onNext("\n--------------------------------------------------------------------\n" + "info: " + info);
//                        break;
//                    }
//                }
//            }
//            subscriber.onCompleted();
//        });
//    }
//
//    private static String getCurrentTime() {
//        String str = "";
//        final Calendar mCalendar = Calendar.getInstance();
//        mCalendar.setTimeInMillis(System.currentTimeMillis());
//        str += mCalendar.get(Calendar.YEAR) + "-";
//        str += mCalendar.get(Calendar.MONTH) + 1 + "-";
//        str += mCalendar.get(Calendar.DAY_OF_MONTH) + " ";
//        str += mCalendar.get(Calendar.HOUR_OF_DAY) + ":";
//        str += mCalendar.get(Calendar.MINUTE) + ":";
//        str += mCalendar.get(Calendar.SECOND);
//        return str;
//    }
//}
