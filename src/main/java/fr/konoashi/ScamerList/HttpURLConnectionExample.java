package fr.konoashi.ScamerList;






import fr.konoashi.ScamerList.enums.Scammer;
import fr.konoashi.ScamerList.utils.References;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpURLConnectionExample {




    public static void main(String url, String url2, String uuidToScan){


        new Thread(() -> {
            References.set_scammer(Scammer.NOT_QUERYED);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            URL obj = null;
            String UUID = uuidToScan;
            try {
                obj = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection con = null;
            try {
                assert obj != null;
                con = (HttpURLConnection) obj.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert con != null;
                con.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            int responseCode = 0;
            try {
                responseCode = con.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("GET Response Code :: " + responseCode);
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String inputLine = null;
            StringBuffer response = new StringBuffer();

            while (true) {
                try {
                    assert in != null;
                    if ((inputLine = in.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.append(inputLine);

            }
            try {
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            URL obj2 = null;
            try {
                obj2 = new URL(url2);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection con2 = null;
            try {
                assert obj2 != null;
                con2 = (HttpURLConnection) obj2.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert con2 != null;
                con2.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            int responseCode2 = 0;
            try {
                responseCode2 = con2.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("GET Response Code :: " + responseCode2);
            BufferedReader in2 = null;
            try {
                in2 = new BufferedReader(new InputStreamReader(
                        con2.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String inputLine2 = null;
            StringBuffer response2 = new StringBuffer();

            while (true) {
                try {
                    assert in2 != null;
                    if ((inputLine2 = in2.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response2.append(inputLine2);

            }
            try {
                in2.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                if (response.toString().contains(UUID) || response2.toString().contains(UUID)) {
                    References.set_scammer(Scammer.IS_SCAMMER);

                } else {
                    if (UUID.equals("invalid name")) {
                        References.set_scammer(Scammer.NO_RECOGNIZED);

                    } else {

                        if (Main.LOCAL_SCAMMER_LIST.contains(UUID)) {
                            References.set_scammer(Scammer.LOCAL_SCAMMER);


                        } else {
                            References.set_scammer(Scammer.NO_SCAMMER);

                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }).start();
        System.out.println("GET DONE");


        }
    }
