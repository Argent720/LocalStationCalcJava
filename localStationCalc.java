
public class localStationCalc {
    private double Lat = 47.1201;
    private double mmHg_kPa = 0.1334;
    private double R = 286.8;
    public double Tslope;
    public double Tcorrection;
    public double latCorrection;
    public double localPressure;
    public double localPressurekPa;
    public double airDensity;
    public double localPressure_UNC;
    public double localPressurekPa_UNC;
    public double airDensity_RU;
    public double airDensity_UNC;
    
    /**
     * Calculates the local station data using the temperature and mercury height.
     * 
     * @param temp
     * @param mercHeight
     */
    public void localStationCalc (double temp, double mercHeight) {
       

        // Calculates the temperature correction factor.
        if (temp == 21) {
            Tslope = (22 - 20) / ((-2.73) - (-2.39));

            Tcorrection = (-1) * ((22 -21) / Tslope) - (-2.73);
        }
        else if (temp > 21 && temp < 22) {
            double Tslope700 = (22 - 21) / ((-2.51) - (-2.39));

            double TCor700 = ((22 - temp) / Tslope700) - (-2.51);

            double Tslope800 = (22 - 21) / ((-2.86) - (-2.73));

            double TCor800 = ((22 - temp) / Tslope800) - (-2.86);

            Tslope = (22 - 21) / (TCor800 - TCor700);

            Tcorrection = -1 * ((22 - temp) / Tslope) - (TCor800);
        }
        else if (temp == 22) {
            Tslope = (23 -21) / ((-2.86) - (-2.51));

            Tcorrection = (-1) * ((23 - 22) / Tslope) - (-2.86);
        }
        else {
            System.out.println("Invalid Temperature!");
        }

        // Calculates the latitude correction factor.
        double LatSlope700 = (48 - 47) / (0.16 - 0.09);

        double LatCor700 = ((48 - Lat) / LatSlope700) - (0.16);

        double LatSlope800 = (48 - 47) / (0.18 - 0.11);

        double LatCor800 = ((48 - Lat) / LatSlope800) - (0.18);

        double LatSlope = (48 - 47) / (LatCor800 - LatCor700);

        latCorrection = ((48 - Lat) / LatSlope) - (LatCor800);

        // Calculates the local pressure.
        localPressure = mercHeight - Tcorrection + latCorrection;

        // Converts local pressure to kPa.
        localPressurekPa = localPressure * mmHg_kPa;

        // Calculates the air density.
        airDensity = (localPressurekPa * 1000) / (R * (temp + 273.15));

        // Relative uncertainty calculations.
        localPressure_UNC = localPressure * (0.05 / mercHeight);

        localPressurekPa_UNC = (localPressure_UNC * 133.4) / 1000;

        airDensity_RU = (Math.sqrt(Math.pow(localPressurekPa_UNC / localPressurekPa, 2) + Math.pow(0.5 / temp, 2))) * 100;

        airDensity_UNC = airDensity * (airDensity_RU / 100);

    }
}
