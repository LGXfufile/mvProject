package com.simba.spider;

/*
@Date 2022/12/5 21:26
@PackageName com.simba.spider
@Author liguangxin
@Descrption 这里描述该类实现的功能
@常用快捷键 try catch 【CTRL+ALT+T】
@常用快捷键 代码格式化 【CTRL+ALT+L】
*/

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MySpider {

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://image.baidu.com/search/acjson?tn=resultjson_com&logid=11373506458734512447&ipn=rj&ct=201326592&is=&fp=result&fr=&word=%E5%94%AF%E7%BE%8E%E5%A5%B3%E7%94%9F%E5%A3%81%E7%BA%B8&queryWord=%E5%94%AF%E7%BE%8E%E5%A5%B3%E7%94%9F%E5%A3%81%E7%BA%B8&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=&hd=&latest=&copyright=&s=&se=&tab=&width=&height=&face=&istype=&qc=&nc=1&expermode=&nojc=&isAsync=&pn=30&rn=30&gsm=1e&1670246120668=");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("Accept", "text/plain, */*; q=0.01");
        httpConn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
        httpConn.setRequestProperty("Cache-Control", "no-cache");
        httpConn.setRequestProperty("Connection", "keep-alive");
        httpConn.setRequestProperty("Cookie", "winWH=%5E6_1463x741; BDIMGISLOGIN=0; BDqhfp=%E7%BE%8E%E5%A5%B3%E5%A3%81%E7%BA%B8%26%26640-10-1undefined%26%264896%26%267; PSTM=1618037952; __yjs_duid=1_e2f8a8ba85ca362f7d5e4d7ac47cbb111618040663473; BIDUPSID=3741E5507816483477499683A3D1F6C2; H_WISE_SIDS=110085_114551_179348_180636_185635_189755_194529_196426_197711_199572_204907_208721_208809_209202_209568_210293_210323_211435_212296_212798_213045_213078_213158_213251_213278_214642_214800_215126_215175_215280_215730_215892_215959_216211_216615_216850_216883_216941_216965_217088_217167_217392_217956_218021_218330_218445_218455_218538_218540_218566_218599_218861_218952_218964_219245_219250_219254_219329_219363_219411_219451_219510_219549_219560_219594_219671_219712_219727_219733_219737_219744_219815_219823_219863_219934_219942_219948_220067_220089_220605_220663_220897_8000097_8000118_8000125_8000149_8000169_8000176_8000181_8000182_8000185_8000190; H_WISE_SIDS_BFESS=110085_114551_179348_180636_185635_189755_194529_196426_197711_199572_204907_208721_208809_209202_209568_210293_210323_211435_212296_212798_213045_213078_213158_213251_213278_214642_214800_215126_215175_215280_215730_215892_215959_216211_216615_216850_216883_216941_216965_217088_217167_217392_217956_218021_218330_218445_218455_218538_218540_218566_218599_218861_218952_218964_219245_219250_219254_219329_219363_219411_219451_219510_219549_219560_219594_219671_219712_219727_219733_219737_219744_219815_219823_219863_219934_219942_219948_220067_220089_220605_220663_220897_8000097_8000118_8000125_8000149_8000169_8000176_8000181_8000182_8000185_8000190; MCITY=-131%3A; BDUSS=1YxeVEtZTMwblNxMEJqZVJ4WFVCRjFGQ21aUHZ-dGN3UGNGQnZrTlh1Yy1qM3hqRUFBQUFBJCQAAAAAAAAAAAEAAABCzOU0c3VwZXLKrrb-yfrQpAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD4CVWM-AlVjN; BDUSS_BFESS=1YxeVEtZTMwblNxMEJqZVJ4WFVCRjFGQ21aUHZ-dGN3UGNGQnZrTlh1Yy1qM3hqRUFBQUFBJCQAAAAAAAAAAAEAAABCzOU0c3VwZXLKrrb-yfrQpAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD4CVWM-AlVjN; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BAIDUID=9B08320D0C6229E05112162F6BA50B7C:SL=0:NR=10:FG=1; BDSFRCVID=uiDOJeC62reYVfcjZJ7w-t906K5OJa5TH6ao3OPWzZGfI-5FBobMEG0PDf8g0KA-eZn6ogKK0mOTHvDF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF=tJI8oIPhJIvbfP0kKnQSDjoQqxby26nt2mn9aJ5nJDoKefTKb4b8WxTW5gcJq68qWbT9aJDaQpP-HJARXp3R2lI1b4uLtx5GaKruKl0MLpbtbb0xyULVbqIHjxnMBMPjamOnaPQg3fAKftnOM46JehL3346-35543bRTLnLy5KJYMDcnK4-Xj5j-eHQP; delPer=0; PSINO=1; BAIDUID_BFESS=9B08320D0C6229E05112162F6BA50B7C:SL=0:NR=10:FG=1; BDSFRCVID_BFESS=uiDOJeC62reYVfcjZJ7w-t906K5OJa5TH6ao3OPWzZGfI-5FBobMEG0PDf8g0KA-eZn6ogKK0mOTHvDF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF_BFESS=tJI8oIPhJIvbfP0kKnQSDjoQqxby26nt2mn9aJ5nJDoKefTKb4b8WxTW5gcJq68qWbT9aJDaQpP-HJARXp3R2lI1b4uLtx5GaKruKl0MLpbtbb0xyULVbqIHjxnMBMPjamOnaPQg3fAKftnOM46JehL3346-35543bRTLnLy5KJYMDcnK4-Xj5j-eHQP; BA_HECTOR=2lal840k25012l8k0l8kasg41horrjb1g; ZFY=Qfel9gMbLFhMVnhyREtYMEvgcbWZiLOtEwfYFp:BVDOA:C; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; H_PS_PSSID=37857_36548_37521_37841_37766_37866_37800_37760_26350_37790_37881; BDRCVFR[dG2JNJb_ajR]=mk3SLVN4HKm; userFrom=ala; BDRCVFR[-pGxjrCMryR]=mk3SLVN4HKm; BDRCVFR[Txj84yDU4nc]=mk3SLVN4HKm; indexPageSugList=%5B%22%E7%BE%8E%E5%A5%B3%E5%A3%81%E7%BA%B8%22%5D; cleanHistoryStatus=0; BDRCVFR[tox4WRQ4-Km]=mk3SLVN4HKm; ab_sr=1.0.1_NTI2MGExZWY0YWE1ZThiNTBkMmQ4ZmJiMzc3ZmFkN2Y3MDFmZjczMjFmMzNiMTEzOTk0MTMwM2RjZWVkYTViMzQ0ODVjYTMyOTkyODQwNTQ2MDNiODRlMTllMWM0ZjYwMmJkMTM5YjRjMDY4NTZiMWUzZWUwYTQwN2ZkOGQ1ZTAxZGFlYzY5YTAzODY4ZjljODRjYzJlMzk4MWVlMzBmZg==");
        httpConn.setRequestProperty("Pragma", "no-cache");
        httpConn.setRequestProperty("Referer", "https://image.baidu.com/search/index?ct=201326592&cl=2&st=-1&lm=-1&nc=1&ie=utf-8&tn=baiduimage&ipn=r&rps=1&pv=&fm=rs1&word=%E5%94%AF%E7%BE%8E%E5%A5%B3%E7%94%9F%E5%A3%81%E7%BA%B8&oriquery=2020%E6%9C%80%E7%81%AB%E5%A3%81%E7%BA%B8%E5%A5%B3%E7%94%9F%E5%8F%AF%E7%88%B1&ofr=2020%E6%9C%80%E7%81%AB%E5%A3%81%E7%BA%B8%E5%A5%B3%E7%94%9F%E5%8F%AF%E7%88%B1&dyTabStr=MCwzLDYsMSw0LDUsOCwyLDcsOQ%3D%3D");
        httpConn.setRequestProperty("Sec-Fetch-Dest", "empty");
        httpConn.setRequestProperty("Sec-Fetch-Mode", "cors");
        httpConn.setRequestProperty("Sec-Fetch-Site", "same-origin");
        httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
        httpConn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        httpConn.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"107\", \"Chromium\";v=\"107\", \"Not=A?Brand\";v=\"24\"");
        httpConn.setRequestProperty("sec-ch-ua-mobile", "?0");
        httpConn.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        System.out.println(response);
    }
}
