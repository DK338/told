package edu.fgu.dclab;

public class Castle {


    private String CastleName="";
    private String Lorder="";
    private String Area="";
    private int Soldier_count;
    private boolean ownership;
    private int Attack_Soldier=0;
   // private int Defense_Soldier=Soldier_count-Attack_Soldier;

    public Castle(String castleName,String lorder,String area,int soldier_count,boolean ownership){
        setCastleName(castleName);
        setLorder(lorder);
        setArea(area);
        setSoldier_count(soldier_count);
        setOwnership(ownership);
    }

    public String getCastleName() {
        return CastleName;
    }

    public void setCastleName(String castleName) {
        CastleName = castleName;
    }

    public String getLorder() {
        return Lorder;
    }

    public void setLorder(String lorder) {
        Lorder = lorder;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public int getSoldier_count() {
        return Soldier_count;
    }

    public void setSoldier_count(int soldier_count) {
        Soldier_count = soldier_count;
    }

    public void setOwnership(boolean ownership) {
        this.ownership = ownership;
    }

    public boolean getOwnership() {
        return ownership;
    }


}
