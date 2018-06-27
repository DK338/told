package edu.fgu.dclab;

import java.io.*;
import java.net.Socket;
import java.text.MessageFormat;
import java.time.ZonedDateTime;
import java.util.Random;

public class Servant implements Runnable {
    Boolean SHOW_CASTLE=false;
    Boolean ADD_SOLDIER=false;
    Boolean ATTACK=false;



    private int Soldier_count=10000;
    boolean first_enter=false;
    Castle[] castles = new Castle[9];


    private ObjectOutputStream out = null;
    private String source = null;

    private Socket socket = null;

    private ChatRoom room = null;

    public Servant(Socket socket, ChatRoom room) {
        this.room = room;
        this.socket = socket;

        try {
            this.out = new ObjectOutputStream(
                this.socket.getOutputStream()
            );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0;i<9;i++)
        {
           int a=i+1;
            if(i==0)
                castles[i]=new Castle("1號城堡",this.source,"1-1",Soldier_count,true);
            else
                castles[i]=new Castle(String.valueOf(a)+"號城堡","Com_"+String.valueOf(a),
                        "1+"+String.valueOf(a),Soldier_count+add(a),false);
        }

        greet();
    }

    public void process(Message message) {


        switch (message.getType()) {
            case Message.ROOM_STATE:
                this.write(message);
                break;

            case Message.CHAT:

                Command_Competence(message);

                /*if (SHOW_CASTLE==true)
                {
                    for (int i=0;i<castles.length;i++)
                    {
                        if (castles[i].getCastleName().equals(((ChatMessage) message).MESSAGE))
                        {
                            this.write(new ChatMessage("系統","\n"+"城堡名子:"+castles[i].getCastleName()
                                    +"\n領主:"+castles[i].getLorder()
                                    +"\n士兵數:"+castles[i].getSoldier_count()
                            ));
                        }
                    }
                    SHOW_CASTLE=false;
                }
                else
                    this.write(new ChatMessage("系統",String.valueOf(SHOW_CASTLE)));*/



                /*if ("time?".equals(((ChatMessage) message).MESSAGE)) {
                    this.write(new ChatMessage(
                        "MurMur",
                        MessageFormat.format(
                            "目前時間：{0}",
                            ZonedDateTime.now().toString()
                        )
                    ));
                }
                else if ("show_castle".equals(((ChatMessage) message).MESSAGE))
                {



                }
                else {
                    this.room.multicast(message);
                }*/

                break;

            case Message.LOGIN:
                if (this.source == null) {
                    this.source = ((LoginMessage) message).ID;
                    castles[0].setLorder(this.source);
                    this.room.multicast(new ChatMessage(
                        "系統",
                        MessageFormat.format("{0} 進入了遊戲。", this.source)
                    ));
                    this.write(new ChatMessage("系統", "歡迎來到攻城與守城的世界"));

                    this.room.multicast(new RoomMessage(
                        room.getRoomNumber(),
                        room.getNumberOfGuests()
                    ));
                }

                break;



            default:
        }
    }

    public void write(Message message) {
        try {
            this.out.writeObject(message);
            this.out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void greet() {
        String[] greetings = {
            "歡迎來到 攻城與守城",
            "請問你的【暱稱】?"
        };

        for (String msg : greetings) {
            write(new ChatMessage("系統", msg));
        }
    }

    @Override
    public void run() {
        Message message;

        try (
            ObjectInputStream in = new ObjectInputStream(
                this.socket.getInputStream()
            )
        ) {
//            this.process((Message)in.readObject());

            while ((message = (Message) in.readObject()) != null) {
                this.process(message);
            } // od

            this.out.close();
        }
        catch (IOException e) {
            System.out.println("Servant: I/O Exc eption");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    int add(int i)
    {
        return i*5000;
    }

    void Command_Competence(Message message) {
        if ("show_castle".equals(((ChatMessage) message).MESSAGE)) {
            String All_castle = "所有城堡";
            //SHOW_CASTLE=true;
            for (int i = 0; i < castles.length; i++)
                All_castle = All_castle + "\n" + castles[i].getCastleName();

            this.write(new ChatMessage("系統", All_castle));

        } else if (("show_castle:" + castles[0].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            this.write(new ChatMessage("系統", "\n" + "城堡名子:" + castles[0].getCastleName()
                    + "\n領主:" + castles[0].getLorder()
                    + "\n士兵數:" + castles[0].getSoldier_count()
            ));
            //SHOW_CASTLE=false;
        } else if (("show_castle:" + castles[1].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            this.write(new ChatMessage("系統", "\n" + "城堡名子:" + castles[1].getCastleName()
                    + "\n領主:" + castles[1].getLorder()
                    + "\n士兵數:" + castles[1].getSoldier_count()
            ));
            //SHOW_CASTLE=false;
        } else if (("show_castle:" + castles[2].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            this.write(new ChatMessage("系統", "\n" + "城堡名子:" + castles[2].getCastleName()
                    + "\n領主:" + castles[2].getLorder()
                    + "\n士兵數:" + castles[2].getSoldier_count()
            ));
            //SHOW_CASTLE=false;
        } else if (("show_castle:" + castles[3].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            this.write(new ChatMessage("系統", "\n" + "城堡名子:" + castles[3].getCastleName()
                    + "\n領主:" + castles[3].getLorder()
                    + "\n士兵數:" + castles[3].getSoldier_count()
            ));
            //SHOW_CASTLE=false;
        } else if (("show_castle:" + castles[4].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            this.write(new ChatMessage("系統", "\n" + "城堡名子:" + castles[4].getCastleName()
                    + "\n領主:" + castles[4].getLorder()
                    + "\n士兵數:" + castles[4].getSoldier_count()
            ));
            //SHOW_CASTLE=false;
        } else if (("show_castle:" + castles[5].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            this.write(new ChatMessage("系統", "\n" + "城堡名子:" + castles[5].getCastleName()
                    + "\n領主:" + castles[5].getLorder()
                    + "\n士兵數:" + castles[5].getSoldier_count()
            ));
            // SHOW_CASTLE=false;
        } else if (("show_castle:" + castles[6].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            this.write(new ChatMessage("系統", "\n" + "城堡名子:" + castles[6].getCastleName()
                    + "\n領主:" + castles[6].getLorder()
                    + "\n士兵數:" + castles[6].getSoldier_count()
            ));
            //SHOW_CASTLE=false;
        } else if (("show_castle:" + castles[7].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            this.write(new ChatMessage("系統", "\n" + "城堡名子:" + castles[7].getCastleName()
                    + "\n領主:" + castles[7].getLorder()
                    + "\n士兵數:" + castles[7].getSoldier_count()
            ));
            // SHOW_CASTLE=false;
        } else if (("show_castle:" + castles[8].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            this.write(new ChatMessage("系統", "\n" + "城堡名子:" + castles[8].getCastleName()
                    + "\n領主:" + castles[8].getLorder()
                    + "\n士兵數:" + castles[8].getSoldier_count()
            ));
            //SHOW_CASTLE=false;
        } else if ("show_soldier".equals(((ChatMessage) message).MESSAGE)) {
            int ALL_soldier_count = 0;
            for (int i = 0; i < 9; i++) {
                if (castles[i].getLorder() == this.source) {
                    this.write(new ChatMessage("系統", castles[i].getCastleName() + "的士兵數:" + castles[i].getSoldier_count()));
                    ALL_soldier_count = ALL_soldier_count + castles[i].getSoldier_count();
                }

            }
            this.write(new ChatMessage("系統", MessageFormat.format("您目前擁有的總士兵數：{0}", ALL_soldier_count)));
        } else if ("add_soldier".equals(((ChatMessage) message).MESSAGE)) {
            //castles[2].setLorder(this.source);
            String own_castle = "你擁有的城堡:";
            ADD_SOLDIER = true;
            for (int i = 0; i < 9; i++) {
                if (castles[i].getLorder() == this.source) {
                    this.write(new ChatMessage("系統", castles[i].getCastleName() + "的士兵數:" + castles[i].getSoldier_count()));
                }

            }

            this.write(new ChatMessage("系統", MessageFormat.format("請問是否要徵兵：{0}", "(Y/N)")));
            //first_enter=true;
        } else if ("Y".equals(((ChatMessage) message).MESSAGE) && ADD_SOLDIER == true) {
            Random b = new Random();

            int castle_count = 0;
            int All_soldier = 0;
            for (int i = 0; i < 9; i++) {
                if (castles[i].getLorder() == this.source) {
                    int add_count = b.nextInt(2000) + 1001;
                    castles[i].setSoldier_count(castles[i].getSoldier_count() + add_count);
                    this.write(new ChatMessage("系統", MessageFormat.format("您的" + castles[i].getCastleName() + "增加的士兵數：{0}", add_count)));
                    this.write(new ChatMessage("系統", castles[i].getCastleName() + "的士兵數:" + castles[i].getSoldier_count()));
                    All_soldier = All_soldier + castles[i].getSoldier_count();
                }
            }

            this.write(new ChatMessage("系統", MessageFormat.format("您目前擁有的總士兵數：{0}", All_soldier)));
            ADD_SOLDIER = false;
        } else if ("N".equals(((ChatMessage) message).MESSAGE) && ADD_SOLDIER == true) {
            this.write(new ChatMessage("系統", "取消徵兵"));
            ADD_SOLDIER = false;
        } else if ("attack".equals(((ChatMessage) message).MESSAGE)) {
            String All_castle = "可攻擊的城堡";
            for (int i = 0; i < 9; i++) {
                if (castles[i].getLorder() != this.source) {
                    All_castle = All_castle + "\n" + castles[i].getCastleName();
                }
            }
            this.write(new ChatMessage("系統", All_castle));
        } else if (("attack_castle:" + castles[0].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            attack_funition(0);
            //SHOW_CASTLE=false;
        } else if (("attack_castle:" + castles[1].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            attack_funition(1);
        } else if (("attack_castle:" + castles[2].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            attack_funition(2);
        } else if (("attack_castle:" + castles[3].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            attack_funition(3);
        } else if (("attack_castle:" + castles[4].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            attack_funition(4);
        } else if (("attack_castle:" + castles[5].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            attack_funition(5);
        } else if (("attack_castle:" + castles[6].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            attack_funition(6);
        } else if (("attack_castle:" + castles[7].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            attack_funition(7);
        } else if (("attack_castle:" + castles[8].getCastleName()).equals(((ChatMessage) message).MESSAGE)) {
            attack_funition(8);
        } else
        {
            ADD_SOLDIER=false;
            this.write(message);
        }


    }

    void attack_funition(int castles_location)
    {
        if(castles[castles_location].getLorder()==this.source)
        {
            this.write(new ChatMessage("系統","你不能攻擊自已"));
        }
        else
        {
            int All_soldier_count=0;
            int attack_soldier=0;
            for (int i=0;i<castles.length;i++)
            {
                if(castles[i].getLorder()==this.source)
                {
                    this.write(new ChatMessage("系統","從"+castles[i].getCastleName()+"徵調"+castles[i].getSoldier_count()));
                    All_soldier_count=All_soldier_count+castles[i].getSoldier_count();
                    castles[i].setSoldier_count(0);

                }
            }
            this.write(new ChatMessage("系統","對"+castles[castles_location]+"發起進攻"));
            this.write(new ChatMessage("系統","總兵力:"+All_soldier_count));
            this.write(new ChatMessage("系統","敵方兵力:"+castles[castles_location].getSoldier_count()));
            if (All_soldier_count>castles[castles_location].getSoldier_count()*1.3)
            {
                this.write(new ChatMessage("系統","進攻成功"));
                All_soldier_count=All_soldier_count-Integer.valueOf((int) (castles[castles_location].getSoldier_count()*1.3));
                castles[castles_location].setLorder(this.source);
                castles[castles_location].setOwnership(true);
                castles[castles_location].setSoldier_count(All_soldier_count-castles[castles_location].getSoldier_count());
            }
            else
            {
                castles[castles_location].setSoldier_count(castles[castles_location].getSoldier_count()-All_soldier_count);
                this.write(new ChatMessage("系統","進攻失敗"));
            }
        }
    }



    void list(){
        /*else if ("show_soldier".equals(((ChatMessage) message).MESSAGE))
        {
            this.write(new ChatMessage(
                    "MurMur",
                    MessageFormat.format(
                            "您目前擁有的士兵數：{0}",
                            Soldier_count
                    )
            ));
        }
        else if ("add_soldier".equals(((ChatMessage) message).MESSAGE))
        {
            this.write(new ChatMessage("MurMur", MessageFormat.format("您目前擁有的士兵數：{0}", Soldier_count)));
            this.write(new ChatMessage("MurMur", MessageFormat.format("請問是否要徵兵：{0}", "(Y/N)") ));
            first_enter=true;
        }
        else if ("Y".equals(((ChatMessage) message).MESSAGE)&&first_enter==true)
        {
            Random b = new Random();
            int add_count = b.nextInt(2000) + 1001;
            this.write(new ChatMessage("MurMur", MessageFormat.format("您增加的士兵數：{0}", add_count)));
            Soldier_count = Soldier_count + add_count;
            this.write(new ChatMessage("MurMur", MessageFormat.format("您目前擁有的士兵數：{0}", Soldier_count)));
            first_enter=false;
        }
        else if ("N".equals(((ChatMessage) message).MESSAGE)&&first_enter==true)
        {
            this.write(new ChatMessage("MurMur", "取消徵兵"));
            first_enter=false;
        }*/

    }
}

// Servant.java