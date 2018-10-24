package jon.android.WAM;

/**
 * Created by Jon on 2017-07-07.
 */

public class GraphValues {

    private long gameTime;
    private float sdRT;
    private float medianRT;
    private int GameType;
    private String numMole;
    private String numButterfly;
    private String numMoleHat;
    private String numRaccoon;
    private String waves;
    private int nBack;
    private String nBackType;


    public  GraphValues(long gametime,float sd,float medianrt, int gameType){
        gameTime = gametime;
        sdRT = sd;
        medianRT = medianrt;
        GameType = gameType;

    }

    public void setInhibition( int Mole, int Butterfly, int MoleHat, int Raccon) {
         numMole = Integer.toString(Mole);
         numButterfly = Integer.toString(Butterfly);
         numMoleHat = Integer.toString(MoleHat);
         numRaccoon = Integer.toString(Raccon);
    }
    public void setWaves(boolean wave){
        if(wave){
            waves = "1";
        }else{
            waves = "0";
        }
    }
    public void setNback(int n, String type){
        nBack = n;
        nBackType = type;
    }

    public float getMedianRT(){
        return medianRT;
    }
    public float getSdRT(){
        return sdRT;
    }

    public long getGameTime(){
        return gameTime;
    }

    public int getCondition(){
        if(GameType == 2){
            //left most "bit" is the nback value, the other "bit" if 0 then character if 1 then letters
            if(nBackType.equals("Characters")){
                nBackType = "0";
            }else{
                nBackType = "1";
            }
            return Integer.parseInt(nBack + nBackType);
        }else if(GameType == 0){
            //"bits" go mole,butterfly,molehat,raccoon,wave where wave 0 if wave off 1 if wave on
            return Integer.parseInt(numMole + numButterfly + numMoleHat + numRaccoon + waves);
        }
        else{
            //"bits" go mole,butterfly
            return Integer.parseInt(numMole + numButterfly);
        }
    }

}
