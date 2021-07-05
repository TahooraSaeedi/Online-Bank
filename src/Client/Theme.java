package Client;

public abstract class Theme {
    public static int theme = 3;
    public static String font1 = "Gabriola";//برای لیبل ها و اکثریت نوشته ها
    public static String font2 = "Arial Rounded MT Bold";//برای قسمت هایی که قراره پر شن و عبارت هایی که شامل عدد هستن
    public static String button1;//بکگراند دکمه های اصلی در صفحه ورودی
    public static String button2;//بکگراند دکمه های یس و نو
    public static String back1;//بکگراند صفحات کوچک با دکمه هوم
    public static String back2;//بکگراند صفحات یکم بزرگ تر مثل افتتاح حساب
    public static String back3;//بکگراند مستطیل نمایش حساب ها
    public static String text1;//برای تیتر ها
    public static String text2;//نوشته های دکمه های اصلی در صفحه ورودی
    public static String text3;//متن کمرنگ در قسمت های پرکردنی
    public static String field;//بکگراند قسمت های پرکردنی

    static {
        switch (theme) {
            case 1: {
                button1 = "#A8DADC";
                button2 = "#E63946";
                back1 = "#457B9D";
                back2 = "#A8DADC";
                back3 = "#F1FAEE";
                text1 = "#F1FAEE";
                text2 = "#1D3557";
                text3 = "#A8DADC";
                field = "#F1FAEE";
                break;
            }
            case 2: {
                button1 = "#E5E5E5";
                button2 = "#1E325C";
                back1 = "#E5E5E5";
                back2 = "#000000";
                back3 = "#E5E5E5";
                text1 = "#000000";
                text2 = "#14213D";
                text3 = "#D6D6D6";
                field = "#FFFFFF";
                break;
            }
            case 3: {
                button1 = "#F7EDE2";
                button2 = "#84A59D";
                back1 = "#F7EDE2";
                back2 = "#F6BD60";
                back3 = "#F7EDE2";
                text1 = "#F28482";
                text2 = "#F28482";
                text3 = "#EDD7BF";
                field = "#F7EDE2";
                break;
            }
        }
    }
}