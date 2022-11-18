package InboundFlow;

import java.util.Random;

public class data {
    public static String baseUrl = "https://wms-dev.lbcshop2ship.com";
    static Random randomNumber = new Random();
    public static int trackingId = randomNumber.nextInt(200000000);

    public static String customerAccount = "10-0000033";

    public static String token = "";
    public static String binCode = "1-1-A";
    public static int rfId = randomNumber.nextInt(200000000);
    public static int packageId ;
    public static String imageUrl;
    public static int itemGroupsId;
    public static String igId ;
    public static String userName = "wms.user1@email.com";

//Bin Management
    public static String statusOfBin = "Active"; //Active or Inactive
    public static String type = "Box"; //Box or Pouch

    public static String newBinCode = "33-33-I";
    public static int binId;

    //Pack
    //for pick completed status parcel
    public static String pickCompletedParcelId = "10000005966";
    public static String orderCode;
    public static int id;

    //for packing status parcel
    public static String packingParcelId = "10000003607";
    public static String orderCodeForPacking;
    public static int idForPacking;

    //for reject packing
    public static String pickCompletedParcelIdForReject = "10000007467";
    public static int idForReject;
    public static String orderCodeForReject;

}


