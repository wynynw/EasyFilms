package com.example.and319;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Looper;
import java.io.*;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


import javax.net.ssl.HttpsURLConnection;

public class Client {

    static CountDownLatch latch;

    public static void main(String[] args) throws UnknownHostException, IOException {
        connect2Server c2s = new connect2Server("us-or-cera-1.natfrp.cloud", 22398);
        c2s.send("order\n123");
        try {
            latch = new CountDownLatch(1);
            latch.await();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println(c2s.objectInfo.size());
    }
}



//class connect2Server implements Runnable {
class connect2Server {
    //this array is used to store the info get from server
    public ArrayList<String> stringInfo;
    public ArrayList<Object> objectInfo;
    private Socket socket;
    private InputStream is;
    private ObjectInputStream ois;
    private PrintWriter writer;
    private String host;
    private int port;
    //static byte[] tmp_image;
    static ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    //private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private Bitmap bitmap;
    static Bitmap singleBitap;

    public InputStream getIs() {
        return this.is;
    }
    //this class is used for sending data from app to the server
    connect2Server(String host, int port) {
        this.host = host;
        this.port = port;
        stringInfo = new ArrayList<String>();
        objectInfo = new ArrayList<Object>();
    }

    int send(String messages) {
        int threadNum = 1;
        final String tmp = messages;
        //final CountDownLatch count = new CountDownLatch(threadNum);

        //waiting to be solved

        new Thread() {
            public void run() {
                try {
                    // create a socket and connect to a specific port
                    socket = new Socket(host, port);
                    //create an output stream with a buffer to send data to the server
                    writer = new PrintWriter(socket.getOutputStream());
                    writer.println(tmp);
                    writer.flush();
                    Boolean result = getText();
                    socket.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }.start();
        System.out.println("DONE!!!");
        return 0;
    }

    public ArrayList<Bitmap> getBitmaps(){
        return bitmaps;
    }

    private Boolean getText() {
        try {
            // initialise the stream
            ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            //get the input information
            Object data = ois.readObject();
//            System.out.println(data.getClass());
            if (data instanceof String) {
                stringInfo.clear();
                stringInfo.add((String)data);
            } else if (data instanceof Personal_info || data instanceof MovieDetail) {
                objectInfo.clear();
                objectInfo.add(data);
            } else {
                objectInfo = (ArrayList<Object>) data;
            }
            socket.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        Client.latch.countDown();
        return true;
    }
public boolean isMainThread() {return Looper.getMainLooper() == Looper.myLooper();}
    // this is used for download image from the internet
// the data get from the url will be stored into an ArrayList<String> which will be return later
    public void download(final String address) {  //提示加final，不知道对不对
        //tmp_image = new byte[1024 * 1024];
        new Thread() {
            public void run() {
                try {
                    URL image = new URL(address);
                    HttpsURLConnection getImage = (HttpsURLConnection) image.openConnection();
                    getImage.setConnectTimeout(3 * 1000);
                    is = getImage.getInputStream();

                    bitmap = BitmapFactory.decodeStream(is);

                    if(bitmap == null){
                        //System.out.println("bitmap解析出问题了\n");
                        //System.out.println("大小为： " + bitmap.getByteCount());
                        Bitmap defaultBitmap = Bitmap.createBitmap( 1, 1, Bitmap.Config.ARGB_8888 );
                        bitmaps.add(defaultBitmap);
                        singleBitap = bitmap;
                    }
                    else{
                        //System.out.println("图片大小为： " + bitmap.getByteCount());
                       // System.out.println(bitmaps.size());
                        bitmaps.add(bitmap);
                        singleBitap = bitmap;
                    }
                    is.close(); //关闭流试试
                    Client.latch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static byte[] readStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
}





class account {
    // store the user info in memory
    private String ID;
    private String Password;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPassword(String password) {
        Password = password;
    }

    String getID() {
        return ID;
    }

    String getPassword() {
        return Password;
    }

    account(String ID, String Password) {
        this.ID = ID;
        this.Password = Password;
    }
}

class Seat implements Serializable {
    private static final long serialVersionUID = -4957991707351585067L;
    private int x;
    private int y;
    private int room;

    Seat(int x, int y, int room) {
        this.x = x;
        this.y = y;
    }

    Seat() {
    }

    public int getRoom() {
        return room;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "x=" + x +
                ", y=" + y +
                ", room=" + room +
                '}';
    }
}

class Order implements Serializable {
    private static final long serialVersionUID = 4350166073139763567L;
    private String ID, Date, Price, MovieTime, MName, RoomX, RoomY, getTicket, yet;

    Order(String ID, String date, String price, String movieTime, String MName, String X, String Y, String getTicket, String yet) {
        this.ID = ID;
        Date = date;
        Price = price;
        MovieTime = movieTime;
        this.MName = MName;
        RoomX = X;
        RoomY = Y;
        this.getTicket = getTicket;
        this.yet = yet;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ID='" + ID + '\'' +
                ", Date='" + Date + '\'' +
                ", Price='" + Price + '\'' +
                ", MovieTime='" + MovieTime + '\'' +
                ", MName='" + MName + '\'' +
                ", RoomX='" + RoomX + '\'' +
                ", RoomY='" + RoomY + '\'' +
                ", getTicket='" + getTicket + '\'' +
                '}';
    }

    Order() {
    }

    public String getID() {
        return ID;
    }

    public String getDate() {
        return Date;
    }

    public String getPrice() {
        return Price;
    }

    public String getMovieTime() {
        return MovieTime;
    }

    public String getMName() {
        return MName;
    }

    public String getRoomX() {
        return RoomX;
    }

    public String getRoomY() {
        return RoomY;
    }

    public String getYet() {
        return yet;
    }

    public String getTicket() {
        return getTicket;
    }
}

class Personal_info implements Serializable {
    private static final long serialVersionUID = 697090861939480539L;
    private String name, phone, email, gender, birthday;

    Personal_info(String name, String phone, String email, String gender, String birthday) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
    }

    Personal_info() {
    }

    @Override
    public String toString() {
        return "Personal_info{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }
}

class OnlineFilm implements Serializable {
    private static final long serialVersionUID = 5284187488527825192L;
    private String mname, time, price, room;

    @Override
    public String toString() {
        return "OnlineFilm{" +
                "mname='" + mname + '\'' +
                ", time='" + time + '\'' +
                ", price='" + price + '\'' +
                ", room='" + room + '\'' +
                '}';
    }

    OnlineFilm(String mname, String time, String price, String room) {
        this.mname = mname;
        this.time = time;
        this.price = price;
        this.room = room;
    }

    public OnlineFilm() {
    }

    public String getMname() {
        return mname;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public String getRoom() {
        return room;
    }
}

class MovieDetail implements Serializable {
    private static final long serialVersionUID = -3758879335544732897L;
    private String mName, poster, timeOn, director, theater, simple, detail, location;

    MovieDetail(String mName, String poster, String timeOn, String director, String theater, String simple, String detail, String location) {
        this.mName = mName;
        this.poster = poster;
        this.timeOn = timeOn;
        this.director = director;
        this.theater = theater;
        this.simple = simple;
        this.detail = detail;
        this.location = location;
    }

    MovieDetail() {
    }

    public String getmName() {
        return mName;
    }

    public String getPoster() {
        return poster;
    }

    public String getTimeOn() {
        return timeOn;
    }

    public String getDirector() {
        return director;
    }

    public String getTheater() {
        return theater;
    }

    public String getDetail() {
        return detail;
    }

    public String getSimple() {
        return simple;
    }

    public String getLocation(){return location;}

    public double[] getLoLa(){
        String[] temp;
        double[] lola=new double[2];
        temp = location.split(",");
        lola[0] = Double.parseDouble(temp[0]);
        lola[1] = Double.parseDouble(temp[1]);
        return lola;
    }


    @Override
    public String toString() {
        return "MovieDetail{" +
                "mName='" + mName + '\'' +
                ", poster='" + poster + '\'' +
                ", timeOn='" + timeOn + '\'' +
                ", director='" + director + '\'' +
//                ", actors=" + actors +
                '}';
    }
}


