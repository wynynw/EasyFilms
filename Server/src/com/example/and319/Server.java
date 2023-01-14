package com.example.and319;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static java.lang.System.*;


public class Server {

    public static void main(String[] args) {
        // this server socket is used to listen to the port
        ServerSocket ss = null;
        // this arraylist is used to store the current-online account id
        try {
            ss = new ServerSocket(12333);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        while (true) {
            try {
                Socket newSocket = ss.accept();
                if (newSocket != null)
                    new ClientHandler(newSocket).start();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread {

    private Socket socket;
    private String ID;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    private Boolean send(Object messages) {
        try {
            if (messages instanceof String) {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());  //String浼犺繃鍘讳笉鏄竴涓璞℃祦锛屾墍浠ヤ細鍑虹幇鏁版嵁娴佸ご灏惧涓嶄笂鏍囧噯鐨勫璞℃祦锛屼粠鑰屽鎴风灏辨病娉曟妸inputstre杞负Objectinputstream
                oos.writeObject(messages);
                oos.flush();
                System.out.println("send: " + messages);
                return true;
            } else {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(messages);
                oos.flush();
                System.out.println("send: " + messages.toString());
                return true;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    public void run() {
        try {
            //DB object is used to establish a connection to DB
            con_db DB = new con_db();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //create input and output stream
            String command;
            command = br.readLine();
            System.out.println(command);
            if (command.equals("signup")) {
                System.out.println("Signing Up");
                String ID = br.readLine();
                String Phone = br.readLine();
                String email = br.readLine();
                String password = br.readLine();
                System.out.println(ID + " " + Phone + " " + email + " " + password);
                int result = DB.signUp(ID, Phone, email, password);
                if (result == 0) {
                    send("0");
                } else if (result == -1) {
                    send("-1");
                } else {
                    send("-2");
                }
            } else if (command.equals("login")) { //no matter what status the Client is, app should do login action once it is open
                System.out.println("Login");
                String ID = br.readLine();
                String password = br.readLine();
                System.out.println("ID: " + ID);
                System.out.println("PWD" + password);
                boolean result = DB.login(ID, password);
                //succeed to login
                if (result) {
//                    Server.online.add(ID);
                    this.ID = ID;
                    send("1");
                    System.out.println(this.ID + ": succeed to login");
                }
                //wrong password
                else {
                    send("0");
                    System.out.println(this.ID + ": wrong password");
                }
            } else if (command.equals("changepassword")) {
                String id = br.readLine();
                String old = br.readLine();
                String newPwd = br.readLine();
                boolean result = DB.changePwd(id, old, newPwd);
                //succeed to change password
                if (result) {
                    System.out.println("succeed to change password");
                    send("1");
                }
                //fail to change password
                else {
                    send("0");
                    System.out.println("fail to change password");
                }
            } else if (command.equals("order")) {
                String id = br.readLine();
                send(DB.order(id));
                System.out.println(id);
                //System.out.println(DB.order(id));
            } else if (command.equals("movie")) {
                String movieName = br.readLine();
                send(DB.movieDetails(movieName));
            } else if (command.equals("show")) {
                send(DB.showMovie());
            } else if (command.equals("schedule")) {
                String name = br.readLine();
                send(DB.showDetail(name));
            } else if (command.equals("buy")) {
                String time = br.readLine(); // this is room and time
                int room = Integer.parseInt(br.readLine());
                //get movie name and send arrangement
                //get arrangement (time, room), send seat;
                out.println(time + " " + room);
                send(DB.occupy(time, room));
            } else if (command.equals("preorder")) {
                // "0" - card
                // "1" - cash
                String judge = br.readLine();
                String password = br.readLine();
                int t = Integer.parseInt(br.readLine());
                String time = br.readLine();
                String date = br.readLine();
                String usr_name = br.readLine();
                String mname = br.readLine();
                int room = Integer.parseInt(br.readLine());
                double cost = Double.parseDouble(br.readLine());
                ArrayList<Seat> seats = new ArrayList<Seat>();
                for (int i = 0; i < t; i++) {
                    int x = Integer.parseInt(br.readLine());
                    int y = Integer.parseInt(br.readLine());
                    out.println(x + " " + y);
                    seats.add(new Seat(x, y, room));
                }
                String address = br.readLine();
                String code = DB.preorder(t, time, room, seats, password, date, usr_name, mname, cost);
                send(code);

                if (!code.equals("0")) {
                    try {
                        String datetime = date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8) +
                                "   " + time.substring(0, 2) + ":" + time.substring(2, 4) + " ~ " + time.substring(4, 6) +
                                ":" + time.substring(6, 8);
                        String seat = new String();
                        for (Seat s : seats) {
                            seat += s.getX() + " line, " + s.getY() + " row\n";
                        }
                        String message;
                        if (judge.equals("0")) {
                            message = "Your code is: " + code +
                                    "<br>" + "Movie Name: " + mname +
                                    "<br>" + "Time: " + datetime +
                                    "<br>" + "Room : " + room +
                                    "<br>" + "Seat: " + seat +
                                    "<br>" + "Price: " + cost + " yuan";
                        } else {
                            message = "Your code is: " + code +
                                    "<br>" + "Movie Name: " + mname +
                                    "<br>" + "Time: " + datetime +
                                    "<br>" + "Room : " + room +
                                    "<br>" + "Seat: " + seat +
                                    "<br>" + "Price: " + cost + " yuan\n" + "Please pay for these tickets in the cinema before the start of this movie.\n";
                        }
                        Sendmail.send(message, address);
                        System.out.println(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                /*
                preorder\npassword\nnum\ntime\ndate\nusername\nmoviename\nroom\ncost\nxy\xy\xy
                 */
            } else if (command.equals("personal")) {
                String id = br.readLine();
                send(DB.personal(id));
            } else if (command.equals("logout")) {
                // this is used for logout
                System.out.println("Log out");
                String id = br.readLine();
                send("0");
                DB.logOut(id);
            } else if (command.equals("email")) {
                String id = br.readLine();
                String email = br.readLine();
                send(String.valueOf(DB.changeEmail(id, email)));
            } else if (command.equals("phone")) {
                String id = br.readLine();
                String phone = br.readLine();
                send(String.valueOf(DB.changePhone(id, phone)));
            } else if (command.equals("gender")) {
                String id = br.readLine();
                String gender = br.readLine();
                send(String.valueOf(DB.changeGender(id, gender)));
            } else if (command.equals("birth")) {
                String id = br.readLine();
                String birth = br.readLine();
                send(String.valueOf(DB.changeBirth(id, birth)));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
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
    private String time;

    public String getTime() {
        return time;
    }

    Seat(int x, int y, int room, String time) {
        this.x = x;
        this.y = y;
        this.room = room;
        this.time = time;
    }

    Seat(int x, int y, int room) {
        this.x = x;
        this.y = y;
        this.room = room;
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
    private String ID, Date, Price, MovieTime, MName, RoomX, RoomY, Seat, Account;

    Order(String ID, String date, String price, String movieTime, String MName, String X, String Y, String Seat, String Account) {
        this.ID = ID;
        Date = date;
        Price = price;
        MovieTime = movieTime;
        this.MName = MName;
        RoomX = X;
        RoomY = Y;
        this.Seat = Seat;
        this.Account = Account;
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
                ", Seat ='" + Seat + '\'' +
                ", Account = '" + Account + '\'' +
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

    public String getSeat() {
        return Seat;
    }

    public String getAccount() {
        return Account;
    }
}

class BookDetail implements Serializable {
    private static final long serialVersionUID = 7075844393937045877L;

    private String mName, time, seat, room;

    @Override
    public String toString() {
        return "BookDetail{" +
                "mName='" + mName + '\'' +
                ", time='" + time + '\'' +
                ", seat='" + seat + '\'' +
                ", room='" + room + '\'' +
                '}';
    }

    public BookDetail() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getmName() {
        return mName;
    }

    public String getTime() {
        return time;
    }

    public String getSeat() {
        return seat;
    }

    public String getRoom() {
        return room;
    }

    public BookDetail(String mName, String time, String seat, String room) {
        this.mName = mName;
        this.time = time;
        this.seat = seat;
        this.room = room;
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
    private String mName, poster, timeOn, director, theater, simple, detail, type,location;

    MovieDetail(String mName, String poster, String timeOn, String director, String theater, String simple, String detail, String type, String location) {
        this.mName = mName;
        this.poster = poster;
        this.timeOn = timeOn;
        this.director = director;
        this.theater = theater;
        this.simple = simple;
        this.detail = detail;
        this.type = type;
        this.location=location;
    }

    MovieDetail() {
    }

    public String getmName() {
        return mName;
    }

    public String getPoster() {
        return poster;
    }

    public String getType() {
        return type;
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
    public String getLocation(){
    	return location;
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


class Sendmail {
    public static void send(String code, String address) throws Exception {

        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");


        Session session = Session.getInstance(prop);
        session.setDebug(true);
        Transport ts = session.getTransport();
        ts.connect("smtp.qq.com", "275143757@qq.com", "gpcxlriilpsebghj");
        Message message = createSimpleMail(session, address, code);
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    public static MimeMessage createSimpleMail(Session session, String address, String code)
            throws Exception {

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("275143757@qq.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
        message.setSubject("Your Ticket Code!");
        message.setContent(code, "text/html;charset=UTF-8");

        return message;
    }
}


class con_db {
    //this is used to save names to avoid the same name when signingup
    private ArrayList<account> usr_info;
    // this is used to save emails to avoid the same email address when signingup
    private ArrayList<String> email_list;

    private Connection conn; // establish a new connection

    con_db() {
        usr_info = new ArrayList<account>();
        email_list = new ArrayList<String>();
        establish_con();
    }

    private void establish_con() {
        //  Database credentials
        final String USER = "root"; //username
        final String PASS = "12344321";//password
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//        final String DB_URL = "jdbc:mysql://localhost:3306/androidDB?characterEncoding=utf8&" +
//                "useSSL=false&allowPublicKeyRetrieval=true";
        final String DB_URL = "jdbc:mysql://localhost:3306/androidDB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        try {
            Statement stmt; //usd to execute a SQL command
            Statement stmt2; //usd to execute a SQL command
            Statement stmt3; //usd to execute a SQL command
            Class.forName(JDBC_DRIVER);
            System.out.println("success");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            stmt3 = conn.createStatement();
            // used to get the result of each SQL command
            ResultSet rs_name = stmt.executeQuery("SELECT Name FROM Account");
            ResultSet rs_pwd = stmt2.executeQuery("SELECT Password FROM Account");
            ResultSet rs_email = stmt3.executeQuery("SELECT Email FROM Account");
            while (rs_name.next() && rs_pwd.next()) {
                String name = rs_name.getString("Name");
                String password = rs_pwd.getString("Password");
//                System.out.println(name + " " + password); //delete finally
                try {
                    usr_info.add(new account(name, password));
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }
            while (rs_email.next()) {
                String Email = rs_email.getString("Email");
//                System.out.println(Email);
                try {
                    email_list.add(Email);
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }
//            rs_name.close();
//            rs_email.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException se) {
            se.printStackTrace();
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }

    boolean login(String usr, String pwd) {
        for (account thisOne : usr_info) {
            if (thisOne.getID().equals(usr)) {
                if (pwd.equals(thisOne.getPassword())) {
                    String updateSQL = "update Account set status=true where Name='" + usr + "'";
                    return exeSQL(updateSQL);
                } else return false;
            }
        }
        return false;
    }

    ArrayList<Seat> occupy(String time, int room) { // only occupied seat will be store in the db
        ArrayList<Seat> seats = new ArrayList<Seat>();
        String getInfo = "select * from Arrangement where Time='" + time + "' and RoomNo='" + room + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getInfo);
            while (rs.next()) {
                seats.add(new Seat(rs.getInt("X"), rs.getInt("Y"), rs.getInt("RoomNo"), rs.getString("Time")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return seats;
    }

    // this is more likely to make sure whether the seat is available
    String preorder(int num, String time, int room, ArrayList<Seat> seats, String password, String date, String usr_name, String mname, double cost) {
        /*
         one item called id should be add to db so that once the moeny is paid,
         the server can just check the preorder in the BookDetail by id in db and generate the final order, which will be
         in the db finally
         */
    	System.out.println(num+usr_name);
        int tmp = 1;
        try {
            while (tmp++ != num) {
                // this step is used to check whether any of chosen seats are not available
                String check = "select * from androidDB.Arrangement where RoomNo='" + room + "' and Time='" + time + "' and X=" + seats.get(tmp - 1).getX() + " and Y=" + seats.get(tmp - 1).getY();
                out.println(check);
                Statement stmt = conn.createStatement();
                ResultSet SQLResult = stmt.executeQuery(check);
                if (SQLResult.next()) {
                    out.println(SQLResult.getString("X") + " " + SQLResult.getString("Y"));
                    return "0";
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return "0";
        }
        String getNo = new String();
        for (int i = 0; i < num; i++) {
            out.println("count");
            String exeInsert = "insert into androidDB.Arrangement(X, Y, RoomNo, Time) values('" + seats.get(i).getX() + "', '" + seats.get(i).getY() + "', '" + room + "', '" + date + time + "')";
            out.println(exeInsert);
            getNo = afterPay(seats.get(i), date, time, usr_name, mname, cost);
            if (!exeSQL(exeInsert)) return "0";
        }
        return getNo;
    }

    String afterPay(Seat seat, String date, String time, String account, String mname, double price) {
        Random ran = new Random();
        String no = String.valueOf(ran.nextInt(9999999) - 100000);
        String exeInsert = "insert into androidDB.Order(ID, Date, Price ,Account, MovieTime, MName, Seat, RoomX, RoomY) values('" + no + "', '" + date + "', '" + price + "','" + account + "', '" + time + "', '" + mname + "', '" + seat.getRoom() + "','" + seat.getX() + "', '" + seat.getY() + "')";
        exeSQL(exeInsert);
        return no;
    }

    private boolean exeSQL(String updateSQL) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(updateSQL);
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    Personal_info personal(String id) {
        String getInfo = "select * from Account where Name='" + id + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet SQLResult = stmt.executeQuery(getInfo);
            SQLResult.next();
            return new Personal_info(SQLResult.getString("Name"),
                    SQLResult.getString("Phone"), SQLResult.getString("Email"),
                    SQLResult.getString("Gender"), SQLResult.getString("Birth"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return new Personal_info();
        }
    }

    ArrayList<MovieDetail> showMovie() { // show movies
        ArrayList<MovieDetail> movies = new ArrayList<MovieDetail>();
        String getInfo = "select * from Movies";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getInfo);
            while (rs.next()) {
                movies.add(new MovieDetail(rs.getString("Name"),
                        rs.getString("Poster"), rs.getString("Timeon"),
                        rs.getString("Director"), rs.getString("Theater"), rs.getString("Simple"), rs.getString("Detail"), rs.getString("Type"),rs.getString("Location")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return movies;
    }

    MovieDetail movieDetails(String name) {//movie details
        String getInfo = "select * from Movies where Name='" + name + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getInfo);
            rs.next();
            return new MovieDetail(rs.getString("Name"),
                    rs.getString("Poster"), rs.getString("Timeon"), rs.getString("Director"), rs.getString("Theater"), rs.getString("Simple"), rs.getString("Detail"), rs.getString("Type"),rs.getString("Location"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return new MovieDetail();
        }
    }

    ArrayList<OnlineFilm> showDetail(String name) { //show movie details
        ArrayList<OnlineFilm> showInfo = new ArrayList<OnlineFilm>();
        String getInfo = "select * from ShowDetail where MName='" + name + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getInfo);
            while (rs.next()) {
                showInfo.add(new OnlineFilm(rs.getString("MName"), rs.getString("Time"),
                        rs.getString("Price"), rs.getString("Room")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return showInfo;
    }

    ArrayList<Order> order(String id) {
        ArrayList<Order> showInfo = new ArrayList<Order>();
        String getInfo = "select * from AndroidDB.Order where Account='" + id + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getInfo);
            while (rs.next()) {
                showInfo.add(new Order(rs.getString("ID"), rs.getString("Date"),
                        rs.getString("Price"), rs.getString("MovieTime"),
                        rs.getString("MName"), rs.getString("RoomX"),
                        rs.getString("RoomY"), rs.getString("Seat"), rs.getString("Account")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return showInfo;
    }

    void logOut(String id) {
        String updateSQL = "update Account set status=false where Name='" + id + "'";
        exeSQL(updateSQL);
    }

    int signUp(String id, String Phone, String email, String password) {
        if (uniqueUsername(id)) {// return -1 means that the username has been already existed
        } else
            return -1;
        if (uniqueEmail(email)) {
        } else // return 2 means that the email is not unique
            return -2;
        //id and email are both accepted, so they can be stored in the db
        String new_account = "insert into Account (Name, Phone, Email, Password) values('" + id + "','" + Phone + "', '" + email + "', '" + password + "')";
        exeSQL(new_account);
        return 0;
    }

    private boolean uniqueUsername(String usrname) {
        for (account tmp : usr_info) {
            if (tmp.getID().equals(usrname)) return false;
        }
        return true;
    }

    private boolean uniqueEmail(String email) {
        for (String tmp : email_list) {
            if (tmp.equals(email)) return false;
        }
        return true;
    }

    /*boolean changePwd(String id, String old, String newPwd) {
        logOut(id);
        String check_old = "select Password from Account where Name='" + id + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet old_pwd = stmt.executeQuery(check_old);
            old_pwd.next();
            String tmp = old_pwd.getString("Password");
            if (tmp.equals(old)) {
                String updateSQL = "update Account set Password='" + newPwd + "' where Name='" + id + "'";
                return exeSQL(updateSQL);
            } else
                return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }*/
    boolean changePwd(String id, String old, String newPwd) {
        logOut(id);
        String check_old = "select Password from Account where Name='" + id + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet old_pwd = stmt.executeQuery(check_old);
            if (old_pwd.next()) {
                String tmp = old_pwd.getString("Password");
                if (tmp.equals(old)) {
                    String updateSQL = "update Account set Password='" + newPwd + "' where Name='" + id + "'";
                    return exeSQL(updateSQL);
                } else
                    return false;
            } else return false;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    boolean changeBirth(String id, String birth) {
        String updateSQL = "update Account set Birth='" + birth + "' where Name='" + id + "'";
        return exeSQL(updateSQL);
    }

    boolean changeEmail(String id, String newEmail) {
        String updateSQL = "update Account set Email='" + newEmail + "' where Name='" + id + "'";
        return exeSQL(updateSQL);
    }

    boolean changeGender(String id, String gender) {
        String updateSQL = "update Account set Gender='" + gender + "' where Name='" + id + "'";
        return exeSQL(updateSQL);
    }

    boolean changePhone(String id, String phone) {
        String updateSQL = "update Account set Phone='" + phone + "' where Name='" + id + "'";
        return exeSQL(updateSQL);
    }
}
