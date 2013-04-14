package com.doeasy.DayReminder;

import java.util.Calendar; 
import java.util.Date;

public class clsCalendar { 
	private LSDate SolarDate;
	private LSDate LunarDate;

    private boolean bValid=false;

    final private static long[] 	lunarInfo 		= 		new long[] 
    {   	
    	0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2,	//1900
		0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,	//1910
		0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,	//1920
		0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,	//1930
		0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557,	//1940
		0x06ca0,0x0b550,0x15355,0x04da0,0x0a5b0,0x14573,0x052b0,0x0a9a8,0x0e950,0x06aa0,	//1950
		0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,0x0d950,0x05b57,0x056a0,	//1960
		0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,0x0d558,0x0b540,0x0b6a0,0x195a6,	//1970
		0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,	//1980
		0x04af5,0x04970,0x064b0,0x074a3,0x0ea50,0x06b58,0x05ac0,0x0ab60,0x096d5,0x092e0,	//1990
		0x0c960,0x0d954,0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,	//2000
		0x0a950,0x0b4a0,0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,	//2010
		0x07954,0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,	//2020
		0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45,	//2030
		0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0		//2040
    }; 
    //=======================================定义1900-2049年农历春节在对应公历年的日期数据
    final private static int[][]	LFDayInSDate	= 		new int[][]
    {
    	{1,31},{2,19},{2,8},{1,29},{2,16},{2,4},{1,25},{2,13},{2,2},{1,22},
    	{2,10},{1,30},{2,18},{2,6},{1,26},{2,14},{2,4},{1,23},{2,11},{2,1},
    	{2,20},{2,8},{1,28},{2,16},{2,5},{1,24},{2,13},{2,2},{1,23},{2,10},
    	{1,30},{2,17},{2,6},{1,26},{2,14},{2,4},{1,24},{2,11},{1,31},{2,19},
    	{2,8},{1,27},{2,15},{2,5},{1,25},{2,13},{2,2},{1,22},{2,10},{1,29},
    	{2,17},{2,6},{1,27},{2,14},{2,3},{1,24},{2,12},{1,31},{2,18},{2,8},
    	{1,28},{2,15},{2,5},{1,25},{2,13},{2,2},{1,21},{2,9},{1,30},{2,17},
    	{2,6},{1,27},{2,15},{2,3},{1,23},{2,11},{1,31},{2,18},{2,7},{1,28},
    	{2,16},{2,5},{1,25},{2,13},{2,2},{2,20},{2,9},{1,29},{2,17},{2,6},
    	{1,27},{2,15},{2,4},{1,23},{2,10},{1,31},{2,19},{2,7},{1,28},{2,16},
    	{2,5},{1,24},{2,12},{2,1},{1,22},{2,9},{1,29},{2,18},{2,7},{1,26},
    	{2,14},{2,3},{1,23},{2,10},{1,31},{2,19},{2,8},{1,28},{2,16},{2,5},
    	{1,25},{2,12},{2,1},{1,22},{2,10},{1,29},{2,17},{2,6},{1,26},{2,13},
    	{2,3},{1,23},{2,11},{1,31},{2,19},{2,8},{1,28},{2,15},{2,4},{1,24},
    	{2,12},{2,1},{1,22},{2,10},{1,30},{2,17},{2,6},{1,26},{2,14},{2,2}
    };
    //=======================================定义十二生肖
    final private static String[] 	cnLunarNewYear 		= 		new String[]
    {
    	"鼠", 
    	"牛", 
    	"虎", 
    	"兔", 
    	"龙", 
    	"蛇", 
    	"马", 
    	"羊", 
    	"猴", 
    	"鸡", 
    	"狗", 
    	"猪"
    }; 
    //=======================================定义天干
    final private static String[] 	Gan 			= 		new String[]
    {
    	"甲", 
    	"乙", 
    	"丙", 
    	"丁", 
    	"戊", 
    	"己", 
    	"庚", 
    	"辛", 
    	"壬", 
    	"癸"
    }; 
    //=======================================定义地支
    final private static String[] 	Zhi 			= 		new String[]
    {
    	"子", 
    	"丑", 
    	"寅", 
    	"卯", 
    	"辰", 
    	"巳", 
    	"午", 
    	"未", 
    	"申", 
    	"酉", 
    	"戌", 
    	"亥"
    };
    //=======================================定义中国整十字符
    final private static String[] 	chineseTen 		=		new String[]
    {
    	"初", 
    	"十", 
    	"廿", 
    	"卅"
    }; 
    //=======================================定义农历二十四个节气数据
    final private static String[]	solarTerm		=		new String[]
    {
    	"小寒",
    	"大寒",
    	"立春",
    	"雨水",
    	"惊蛰",
    	"春分",
    	"清明",
    	"谷雨",
    	"立夏",
    	"小满",
    	"芒种",
    	"夏至",
    	"小暑",
    	"大暑",
    	"立秋",
    	"处暑",
    	"白露",
    	"秋分",
    	"寒露",
    	"霜降",
    	"立冬",
    	"小雪",
    	"大雪",
    	"冬至"
    };
  //=======================================定义农历二十四个节气描述
    final private static String[]	solarTermDsp	=		new String[]{
		"气候开始寒冷",
		"一年中最冷的时候",
		"春季的开始",
		"降雨开始，雨量渐增",
		"春雷乍动，惊醒了蛰伏在土中冬眠的动物",
		"表示昼夜平分",
		"天气晴朗，草木繁茂",
		"雨量充足而及时，谷类作物能茁壮成长",
		"夏季的开始",
		"麦类等夏熟作物籽粒开始饱满",
		"麦类等有芒作物成熟",
		"炎热的夏天来临",
		"气候开始炎热",
		"一年中最热的时候",
		"秋季的开始",
		"炎热的暑天结束",
		"天气转凉，露凝而白",
		"昼夜平分",
		"露水以寒，将要结冰",
		"天气渐冷，开始有霜",
		"冬季的开始",
		"开始下雪",
		"降雪量增多，地面可能积雪",
		"寒冷的冬天来临"
	};
    final private static int[] sTermInfo = new int[]
    {
    		0,
    		21207,
    		42460,
    		63818,
    		85304,
    		106962,
    		128796,
    		150827,
    		173026,
    		195400,
    		217910,
    		240509,
    		263163,
    		285802,
    		308388,
    		330878,
    		353217,
    		375376,
    		397356,
    		419137,
    		440748,
    		462191,
    		483520,
    		504753
    };
  //=======================================定义公历年节假日数据 *表示放假日
    final private static String[]	sFtv			=		new String[]
    {
    	"0101*元旦节",
    	"0202 湿地日",
    	"0210 气象节",
    	"0214 情人节",
    	"0303 爱耳日",
    	"0305 学雷锋",
    	"0308 妇女节",
    	"0312 植树节",
    	"0314 警察日",
    	"0315 权益日",
    	"0317 国医节,航海日",
    	"0321 森林日",
    	"0322 水日",
    	"0323 气象日",
    	"0324 防治结核病日",
    	"0325 安全教育日",
    	"0401 愚人节",
    	"0407 卫生日",
    	"0422 地球日",
    	"0501*劳动节",
    	"0504 青年节",
    	"0508 红十字日",
    	"0512 护士节",
    	"0515 家庭日",
    	"0531 无烟日", 
    	"0601 儿童节",
    	"0605 环护日",
    	"0606 爱眼日",
    	"0623 奥林匹克日",
    	"0625 土地日",
    	"0626 禁毒日",
    	"0701 香港回归",
    	"0707 抗战纪念日",
    	"0711 人口日",
    	"0801 建军节",
    	"0808 爸爸节",
    	"0815 抗战胜利纪念",
    	"0909 毛泽东逝世纪念",
    	"0910 教师节", 
    	"0914 清洁地球日",
    	"0918 九·一八事变纪念日",
    	"0920 爱牙日",
    	"0927 旅游日",
    	"0928 孔子诞辰",
    	"1001*国庆节",
    	"1004 动物日",
    	"1006 老人节",
    	"1010 辛亥革命",
    	"1013 国际教师节",
    	"1015 盲人节",
    	"1016 粮食日",
    	"1108 记者日",
    	"1109 消防安全日",
    	"1112 孙中山诞辰",
    	"1117 学生节",
    	"1120*彝族年",
    	"1121*问候日、电视日",
    	"1122*彝族年",
    	"1201 艾滋病日",
    	"1203 残疾人日",
    	"1208 儿童电视日",
    	"1209 足球日",
    	"1210 人权日",
    	"1212 西安事变",
    	"1213 南京大屠杀",
    	"1220 澳门回归",
    	"1224 平安夜",
    	"1225 圣诞节",
    	"1226 毛泽东诞辰"
    };
    //=======================================定义农历年中节日数据 *表示放假日
    final private static String[]	lFtv			=		new String[]{
    	"0101 春节",
    	"0115 元宵节",
    	"0505 端午节",
    	"0707 七夕情人节",
    	"0715 中元节",
    	"0815 中秋节",
    	"0909 重阳节",
    	"1208 腊八节",
    	"1224 小年"
    };
    //=======================================定义星期节日
    final private static String[]	wFtv			=		new String[]{
    	"0150 麻风日",
    	"0520 母亲节",
    	"0530 助残日",
    	"0630 父亲节",
    	"0730 被奴役国家周",
    	"0932 和平日",
    	"0940 聋人节,儿童日",
    	"0950 海事日",
    	"1011 住房日",
    	"1013 减灾日",
    	"1144 感恩节"
    };
    //=======================================定义中文0-9数据
    final private static String[]	chiaNum			=		new String[]{
    	"O",
    	"一",
    	"二",
    	"三",
    	"四",
    	"五",
    	"六",
    	"七",
    	"八",
    	"九",
    	"零"
    };
    //=======================================定义1-12月对应农历中文名称数据
    final private static String[]	LunarMonth		=		new String[]
    {
    	"正",
    	"二",
    	"三",
    	"四",
    	"五",
    	"六",
    	"七",
    	"八",
    	"九",
    	"十",
    	"冬",
    	"腊"
    };
    //=======================================定义公历月星座数据
    final private static String[]	cnHoroscope		=		new String[]{
    	"魔羯座",
    	"水瓶座",
    	"双鱼座",
    	"白羊座",
    	"金牛座",
    	"双子座",
    	"巨蟹座",
    	"狮子座",
    	"处女座",
    	"天秤座",
    	"天蝎座",
    	"射手座"
    };

    final private static String[]	weekDayName		=		new String[]{
    	"日",
    	"一",
    	"二",
    	"三",
    	"四",
    	"五",
    	"六"
    };

    final private static int[]		SolarMonth		=		new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
    
    //=============================构造函数
    public 							clsCalendar() 
    {
    	SolarDate=null;
    	LunarDate=null;
    }
    
    //=============================构造函数
    public 							clsCalendar(int _Year,int _Month,int _Day, int _DayType) 
    { 
    	if(!SetDay(_Year,_Month,_Day, _DayType))
    	{
    		SolarDate=null;
        	LunarDate=null;
    	}
    }
    //=============================构造函数
    public 							clsCalendar(int _Month,int _Day, int _DayType) 
    { 
    	if(!SetDay(_Month,_Day, _DayType))
    	{
    		SolarDate=null;
        	LunarDate=null;
    	}
    }
    //=============================设置变量值
    public boolean 					SetDay(int _Year,int _Month,int _Day, int _DayType)
    {
    	if(GetStatus(_Year,_Month,_Day, _DayType))
    	{
    		switch(_DayType)
        	{
    	    	case 1:
    	    		SolarDate=new LSDate(_Year,_Month,_Day,false);
    	    		LunarDate = Solar2Lunar(_Year,_Month,_Day);
    	    		break;
    	    	case 2:
    	    		LunarDate=new LSDate(_Year,_Month,_Day,false);
    	    		SolarDate=Lunar2Solar(_Year,_Month,_Day,false);
    	    		break;
    	    	case 3:
    	    		LunarDate=new LSDate(_Year,_Month,_Day,true);
    	    		SolarDate=Lunar2Solar(_Year,_Month,_Day,true);
    	    		break;
        	}
    	}
        return bValid;
    }
  //=============================设置变量值
    public boolean 					SetDay(int _Month,int _Day, int _DayType)
    {
    	bValid=true;
    	switch(_DayType)
       	{
        	case 1:
        		SolarDate=new LSDate(2010,_Month,_Day,false);
        		break;
        	case 2:
        		LunarDate=new LSDate(2010,_Month,_Day,false);
        		break;
        	case 4:
        		SolarDate=new LSDate(2010,_Month,_Day,false);
        		break;
        	case 5:
        		SolarDate=new LSDate(2009,_Month,_Day,false);
        		break;
       	}
        return bValid;
    }
    public boolean 					GetStatus(int _Year,int _Month,int _Day, int _DayType)
    {
    	bValid=false;
    	if(_Year<1900||_Year>2049||_Month<1||_Month>12||_Day<1||_Day>31)
        {
    		bValid=false;
        }
        else
        {
	        switch(_DayType)
	        {
	        	case 1:
	        		if(SolarMonthDays(_Year,_Month)>=_Day)
	        		{
	        			bValid=true;
	        		}
	        		else
	        		{
	        			bValid=false;
	        		}
	        		break;
	        	case 2:
	        		if(LunarMonthDays(_Year,_Month)>=_Day)
	        		{
	        			bValid=true;
	        		}
	        		else
	        		{
	        			bValid=false;
	        		}
	        		break;
	        	case 3:
	        		if(LunarLeapMonth(_Year)==_Month&&LunarLeapMonthDays(_Year)>=_Day)
	        		{
	        			bValid=true;
	        		}
	        		else
	        		{
	        			bValid=false;
	        		}
	        		break;
	        	default:
	        		bValid=false;
	        		break;
	        }
        }
    	return bValid;
    }
    
    public boolean 					GetStatus()
    {
    	return bValid;
    }

    //==============================传回公历 y年是否为闰年
    final public static boolean 	isSolarLeapYear(int y)
    {
    	return((y%400==0)||((y%100!=0)&&(y%4==0)));
    }
    
    //==============================传回公历 y年某m月的天数
    final public int 				SolarMonthDays(int y,int m)
    {
    	if(m==2&&isSolarLeapYear(y))
    	{
    		return 29;
    	}
    	else
    	{
    		return(SolarMonth[m-1]);
    	}
    }
    
    //==============================传回公历 y年m月d日到y年1月1日的天数
    final private int		SolarDaysInThisYear(int y,int m,int d)
    {
    	int i, iYearDays = 0;
    	for(i=1;i<m;i++)
    	{
    		iYearDays+=SolarMonthDays(y,i);
    	}
    	iYearDays+=d;
    	return(iYearDays);
    }
    
    //==============================传回公历 y年m月d日到19000131的总天数
    final private int			SolarDaysFrom19000131(int y,int m,int d)
    {
    	int i, iYearDays = 0;
    	for(i=1900; i<y; i++)
    	{
    		iYearDays += 365;
    		if(isSolarLeapYear(i))
    		{
    			iYearDays += 1;
    		}
    	}
    	iYearDays+=SolarDaysInThisYear(y,m,d);
    	iYearDays-=31;
    	return(iYearDays);
    }

    //==============================传回农历 y年的总天数 
    final private static int 		LunarYearDays(int y) { 
        int i, sum = 348; 
        for (i = 0x8000; i > 0x8; i >>= 1) { 
            if ((lunarInfo[y - 1900] & i) != 0) sum += 1; 
        } 
        return (sum + LunarLeapMonthDays(y)); 
    } 

    //==============================传回农历 y年闰月的天数 
    final private static int 		LunarLeapMonthDays(int y) { 
        if (LunarLeapMonth(y) != 0) { 
            if ((lunarInfo[y - 1900] & 0x10000) != 0) 
                return 30; 
            else 
                return 29; 
        } else 
            return 0; 
    } 
    
    //==============================传回农历 y年闰哪个月 1-12 , 没闰传回 0 
    final private static int 		LunarLeapMonth(int y) { 
        return (int) (lunarInfo[y - 1900] & 0xf); 
    } 

    //==============================传回农历 y年m月的总天数 
    final private static int 		LunarMonthDays(int y, int m) { 
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0) 
            return 29; 
        else 
            return 30; 
    } 
    
    //==============================传回农历 y年m月d日到y年正月初一的总天数
    final private static int 		LunarDaysInThisYear(int y,int m,int d,boolean isLeapMonth)
    {
    	int i, sum = 0;
    	int iLunarLeapMonth=LunarLeapMonth(y);
    	for(i=1;i<m;i++)
    	{
    		sum	+= LunarMonthDays(y, i); 
    	}
    	if(isLeapMonth&&m==iLunarLeapMonth)
    	{
    		sum += LunarMonthDays(y, m);
    	}
    	if(m>iLunarLeapMonth)
    	{
    		sum	+= LunarLeapMonthDays(y);
    	}
    	sum+=d;
    	return(sum);
    }

    //==============================传回农历 y年的生肖 
    final public String				GetAnimalsYear(int y) 
    { 
        return cnLunarNewYear[(y - 4) % 12]; 
    } 
    final public int GetAnimalsYear()
    {
    	return (LunarDate.Year - 4) % 12+1;
    }
    final public String GetAnimalsString()
    {
    	return cnLunarNewYear[GetAnimalsYear()];
    }
    //==============================传入 月日的offset 传回干支, 0=甲子 
    final private static String 	cyclicalm(int num) 
    { 
        
        return (Gan[num % 10] + Zhi[num % 12]); 
    } 

    //==============================传入 offset 传回干支, 0=甲子 
    final public String 			GetCyclical(int y) 
    { 
        int num = y - 1900 + 36; 
        return (cyclicalm(num)); 
    } 
    
  //================================传入日期，传回星期几
    final public int 				CaculateWeekDay(int _Year,int _Month,int _Day)
    {
    	//拉尔森公式：：计算的日期不准
    	/*if(wCurMonth==1) wCurMonth=13;
    	if(wCurMonth==2) wCurMonth=14;
    	int iCurrWeekDay=(wCurDay+2*wCurMonth+3*(wCurMonth+1)/5+wCurYear+wCurYear/4-wCurYear/100+wCurYear/400)%7;
    	return iCurrWeekDay;*/
    	int a		= (14-_Month)/12;
    	_Year			= _Year-a;   
    	_Month			= _Month+12*a-2;   
    	return (_Day+_Year+_Year/4-_Year/100+_Year/400+(31*_Month)/12)%7;   
    }

  //==============================传入日期，传回中文星期几
    public String 					CaculateWeekDayC()
    {
    	String strWeek;
    	int WeekDay;
    	WeekDay		= CaculateWeekDay(SolarDate.Year,SolarDate.Month,SolarDate.Day);
    	strWeek="星期";
    	strWeek+=weekDayName[WeekDay];
    	return strWeek;
    }
    
    //==============================传入日期，传回中文星期几
    public String 					CaculateWeekDayC(int _Year,int _Month,int _Day)
    {
    	String strWeek;
    	int WeekDay;
    	WeekDay		= CaculateWeekDay(_Year,_Month,_Day);
    	strWeek="星期";
    	strWeek+=weekDayName[WeekDay];
    	return strWeek;
    }
    public String GetCnWeekDay(int _WeekDay)
    {
    	return "星期"+weekDayName[_WeekDay];
    }
    public String GetCnZWeekDay(int _WeekDay)
    {
    	return "周"+weekDayName[_WeekDay];
    }
    //==============================传入两个日期，传回两个时间的天数差
    public int 						GetDaysBetween (Calendar d1, Calendar d2){
        if (d1.after(d2)){ 
            return -1;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2){
            d1 = (Calendar) d1.clone();
            do{
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            }while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }
    
    //==============================传入两个日期，d1是否在d2之后
    public boolean 					d1Afterd2 (Date d1, Date d2){
        if (d1.after(d2)){ 
            return true;
        }
        else
        {
        	return false;
        }
    }
    
    //==============================取得中国农历天对应字符
    public static String 			GetLunarDayString(int day) { 
        
        int n = day % 10; 
        if (day > 30) 
            return ""; 
        if (day == 10) 
            return "初十"; 
        else if(day == 20 )
        	return "二十";
        else if(day == 30)
        	return "三十";
        else
            return chineseTen[day / 10] + chiaNum[n]; 
    } 

    //==============================根据传入的农历数据转换成公历数据
	final private LSDate 			Lunar2Solar(int _Year,int _iMonth,int _Day,boolean bLeap)
    {
    	int iCurSolarYear,iCurSolarMonth,iCurSolarDay;
    	int iTotalDays,iFirstMonth,iFirstDay;
    	int i,  temp=0;
    	//确定农历年月日到该年正月初一的天数
    	iTotalDays				= LunarDaysInThisYear(_Year,_iMonth,_Day,bLeap);

    	//取得该年正月初一的公历日期
    	iFirstMonth				= LFDayInSDate[_Year-1900][0];
    	iFirstDay				= LFDayInSDate[_Year-1900][1];
    	iCurSolarYear			= _Year;

    	//计算出公历所有的天数
    	iTotalDays				+= SolarDaysInThisYear(iCurSolarYear,iFirstMonth,iFirstDay) - 2;
    	//计算出对应的月
    	for(i=1; i<13 && iTotalDays>=0; i++)
    	{
    		temp = SolarMonthDays(iCurSolarYear,i); 
    		iTotalDays -= temp;
    		if(i==12&&iTotalDays>=0)
    		{
    			iCurSolarYear	+= 1;
    			i		= 0;
    		}
    	}
    	if(iTotalDays<0)
    	{ 
    		iTotalDays			+= temp; 
    		--i; 
    	}
    	iCurSolarMonth=i;
    	iCurSolarDay			= iTotalDays + 1;
    	LSDate lsDate =  new LSDate(iCurSolarYear,iCurSolarMonth,iCurSolarDay,false);
    	return lsDate;
    }
	
	//==============================根据传入的公历数据转换成农历数据
	final private LSDate 			Solar2Lunar(int _Year,int _iMonth,int _Day)
	{
		int i,leap=0,temp=0;
		int iTotalDays;
		Boolean bIsLeap=false;
		LSDate lsDate=new LSDate();
		/*---计算到初始时间1900年1月31日的天数：1900-1-1(正月初一)---*/
		iTotalDays = SolarDaysFrom19000131(_Year,_iMonth,_Day);
		

		for(i=1900; i<2050 && iTotalDays>0; i++)
		{
			temp = LunarYearDays(i);
			iTotalDays		-= temp;
		}

		if(iTotalDays<0) 
		{
			iTotalDays		+= temp;
			i--;
		}

		lsDate.Year		= i;

		int iLunarLeapMonth = LunarLeapMonth(i); //闰哪个月

		for(i=1; i<13 && iTotalDays>0; i++)
		{
			//闰月
			if(iLunarLeapMonth>0 && i==(iLunarLeapMonth+1) && bIsLeap==false)
			{
				--i; 
				bIsLeap		= true; 
				temp			= LunarLeapMonthDays(lsDate.Year); 
			}
			else
			{ 
				temp			= LunarMonthDays(lsDate.Year, i); 
			}

				//解除闰月
			if(bIsLeap==true && i==(iLunarLeapMonth+1))
			{
				bIsLeap		= false;
			}
			iTotalDays -= temp;
		}

		if(iTotalDays==0 && leap>0 && i==iLunarLeapMonth+1)
		{
			if(bIsLeap)
			{
				bIsLeap		= false;
			}
			else
			{ 
				bIsLeap		= true; 
				--i;
			}
		}
		if(iTotalDays<0)
		{
			iTotalDays			+= temp; 
			--i; 
		}

		lsDate.Month			= i;
		lsDate.Day			= iTotalDays + 1;
		lsDate.IsLeap		= bIsLeap;
		return lsDate;
	}
	
	//==============================根据当前数据取得公历字符
	public String 					GetSolarMonthDayString()
    {
		return String.format("%02d月%02d日 ",SolarDate.Month,SolarDate.Day);
    	//return String.valueOf(SolarDate.Year)+"年"+String.valueOf(SolarDate.Month)+"月"+String.valueOf(SolarDate.Day)+"日["+GetHoroscopeCnName(SolarDate.Month,SolarDate.Day,0)+"]";
    }
	//==============================根据当前数据取得公历字符
		public String 					GetLunarMonthDayString()
	    {
	    	return LunarMonth[LunarDate.Month-1]+"月"+GetLunarDayString(LunarDate.Day)+"["+GetAnimalsYear(LunarDate.Year)+"]";
	    }
	//==============================根据当前数据取得公历字符
	public String 					GetSolarString()
    {
		return String.format("%d年%02d月%02d日 ",SolarDate.Year,SolarDate.Month,SolarDate.Day)+CaculateWeekDayC(SolarDate.Year,SolarDate.Month,SolarDate.Day);
		
    	//return String.valueOf(SolarDate.Year)+"年"+String.valueOf(SolarDate.Month)+"月"+String.valueOf(SolarDate.Day)+"日["+GetHoroscopeCnName(SolarDate.Month,SolarDate.Day,0)+"]";
    }
	//==============================根据当前数据取得公历字符
	public String 					GetShortSolarString()
    {
		return String.format("%s　%02d/%02d ",CaculateWeekDayC(SolarDate.Year,SolarDate.Month,SolarDate.Day),SolarDate.Day,SolarDate.Month);
    	//return String.valueOf(SolarDate.Year)+"年"+String.valueOf(SolarDate.Month)+"月"+String.valueOf(SolarDate.Day)+"日["+GetHoroscopeCnName(SolarDate.Month,SolarDate.Day,0)+"]";
    }
	//==============================根据当前数据取得农历字符
    public String 					GetLunarString() 
    { 
    	String strLunar=chiaNum[(int)(LunarDate.Year/1000)];
    	strLunar+=chiaNum[(int)(LunarDate.Year/100%10)];
    	strLunar+=chiaNum[(int)(LunarDate.Year/10%10)];
    	strLunar+=chiaNum[(int)(LunarDate.Year%10)];
    	strLunar+="年";
    	if(LunarDate.IsLeap)
    	{
    		strLunar+="闰";
    	}
    	strLunar+=LunarMonth[LunarDate.Month-1]+"月"+GetLunarDayString(LunarDate.Day)+"["+GetAnimalsYear(LunarDate.Year)+"]";
    	return strLunar;
    } 
	//==============================根据当前数据取得农历字符
    public String 					GetLunarShortString() 
    { 
    	String strLunar="";
    	if(LunarDate.IsLeap)
    	{
    		strLunar+="闰";
    	}
    	strLunar+=LunarMonth[LunarDate.Month-1]+"月"+GetLunarDayString(LunarDate.Day)+"["+GetAnimalsYear(LunarDate.Year)+"]";
    	return strLunar;
    } 
    public LSDate GetLunarLSDateBySolarDate(Date dt)
	{
		LSDate lsDate=Solar2Lunar(dt.getYear()+1900,dt.getMonth()+1,dt.getDate());
		return lsDate;
	}
	public String GetLunarCnDayName(int _Month,int _Day,boolean isLeap)
	{
		String sCnDayName="";
		if(_Day==1)
		{
			if(isLeap)
			{
				sCnDayName="润";
			}
			sCnDayName+=LunarMonth[_Month-1]+"月";
		}
		else
		{
			sCnDayName=GetLunarDayString(_Day);
		}
		return sCnDayName;	
	}
	
    public String					GetHolidayString()
    {
    	String sHoliday="";
    	String sSolarNum=String.format("%02d%02d",SolarDate.Month,SolarDate.Day);
    	int iCount=sFtv.length;
    	for(int i=0;i<iCount;i++)
    	{
    		if(sSolarNum==sFtv[i].substring(0,3))
    		{
    			sHoliday+=sFtv[i].substring(5,sFtv[i].length()-1)+",";
    			break;
    		}
    	}
    	String sLunarNum=String.format("%02d%02d",LunarDate.Month,LunarDate.Day);
    	iCount=lFtv.length;
    	Date dSolar=new Date();
		dSolar.setYear(SolarDate.Year);
		dSolar.setMonth(SolarDate.Month);
		dSolar.setDate(SolarDate.Day);
		int iCWeekDay=dSolar.getDay();
		dSolar.setDate(1);
		int iFWeekDay=dSolar.getDay();
    	for(int i=0;i<iCount;i++)
    	{
    		if(sLunarNum==lFtv[i].substring(0,3))
    		{
    			sHoliday+=lFtv[i].substring(5,lFtv[i].length()-1)+",";
    			break;
    		}
    	}
    	iCount=wFtv.length;
    	//int iFWeekDay=CaculateWeekDay(SolarDate.Year,SolarDate.Month,1);
    	//int iCWeekDay=CaculateWeekDay(SolarDate.Year,SolarDate.Month,SolarDate.Day);
    	int iWeekNum=(int)((SolarDate.Day+iFWeekDay)/7);
    	String sWeekDayNum=String.format("%02d%d%d",SolarDate.Month,iWeekNum,iCWeekDay);
    	for(int i=0;i<iCount;i++)
    	{
    		if(sWeekDayNum==wFtv[i].substring(0,3))
    		{
    			sHoliday+=wFtv[i].substring(5,wFtv[i].length()-1)+",";
    			break;
    		}
    	}
    	sHoliday+=GetSoralTerm(SolarDate.Year,SolarDate.Month,SolarDate.Day);
    	if(sHoliday!="")
    	{
    		sHoliday=sHoliday.substring(0,sHoliday.length()-2);
    	}
    	return sHoliday;
    }
    public String					GetSoloarHoliday()
    {
    	return GetSoloarHoliday(String.format("%02d%02d", SolarDate.Month,SolarDate.Day));
    }
    public String					GetSoloarHoliday(String _SolarNum)
    {
    	String sHoliday="";
    	int iCount=sFtv.length;
    	for(int i=0;i<iCount;i++)
    	{
    		if(_SolarNum.equalsIgnoreCase(sFtv[i].substring(0,4)))
    		{
    			sHoliday+=sFtv[i].substring(5,sFtv[i].length());
    			break;
    		}
    	}
    	return sHoliday;
    }
    public String 					GetLunarHoliday()
    {

    	return GetLunarHoliday(String.format("%02d%02d", LunarDate.Month,LunarDate.Day));
    }
    public String 					GetLunarHoliday(String _LunarNum)
    {
    	String sHoliday="";
    	int iCount=lFtv.length;
    	for(int i=0;i<iCount;i++)
    	{
    		if(_LunarNum.equalsIgnoreCase(lFtv[i].substring(0,4)))
    		{
    			sHoliday+=lFtv[i].substring(5,lFtv[i].length());
    			break;
    		}
    	}
    	return sHoliday;
    }
    
   
    public String 					GetWeekHoliday()
    {
    	String sHoliday="";
    	Date dt=new Date(SolarDate.Year-1900,SolarDate.Month-1,SolarDate.Day);
    	Date dtF=new Date(SolarDate.Year-1900,SolarDate.Month-1,1);
    	int imDays=SolarMonthDays(SolarDate.Year, SolarDate.Month);
    	Date dtE=new Date(SolarDate.Year-1900,SolarDate.Month-1,imDays);
    	sHoliday=GetWeekHoliday(SolarDate.Month,SolarDate.Day,dt.getDay(),dtF.getDay(),dtE.getDay(),imDays);
    	dt=null;
    	dtF=null;
    	dtE=null;
    	return sHoliday;
    }
    public String					GetWeekHoliday(int _Month,int _Day,int _CWeekDay,int _FWeekDay,int _LWeekDay,int _MonthDays)
    {
    	String sHoliday="";
    	int iCount=wFtv.length;
    	for(int i=0;i<iCount;i++)
    	{
    		int iMonth=Integer.parseInt(wFtv[i].substring(0,2));
    		if(_Month>iMonth)
    		{
    			continue;
    		}
    		if(_Month<iMonth)
    		{
    			break;
    		}
    		if(iMonth==_Month&&Integer.parseInt(wFtv[i].substring(3,4))==_CWeekDay )
    		{
    			int iWeek=Integer.parseInt(wFtv[i].substring(2,3));
				int cDay=0;
				if(iWeek==5)
				{
					if(_LWeekDay>=_CWeekDay)
					{
						cDay=_MonthDays-_LWeekDay+_CWeekDay;
					}
					else
					{
						cDay=_MonthDays-_LWeekDay+(_CWeekDay-7);
					}
				}
				else
				{
					int iWeekNum=0;
					if(_FWeekDay<=_CWeekDay)
					{
						iWeekNum=1;
					}
					cDay=(iWeek-iWeekNum)*7-_FWeekDay+_CWeekDay+1;
				}
    			if(cDay==_Day)
    			{
    				sHoliday+=wFtv[i].substring(5,wFtv[i].length());
    				break;
    			}
    			else
    			{
    				continue;
    			}
    			
    		}
    	}
    	return sHoliday;
    }
    public int 						GetHoroscopeNum(int m,int d,int BaseNum)
    {
    	//魔羯座 12月22-1月20
    	//水瓶座 1月21-2月19
    	//双鱼座 2月20-3月20
    	//白羊座 3月21-4月20
    	//金牛座 4月21-5月21
    	//双子座 5月22-6月21
    	//巨蟹座 6月22-7月22
    	//狮子座 7月23-8月23
    	//处女座 8月24-9月23
    	//天秤座 9月24-10月23
    	//天蝎座 10月24-11月22
    	//射手座 11月23-12月21
    	final int[] MONTH_CutOffPoint = new int[]{20,19,20,20,21,21,22,23,23,23,22,21}; 
    	int iPos	= m-1;
    	if(d>MONTH_CutOffPoint[iPos])
    	{
    		iPos	= iPos+1;
    	}
    	return iPos%12+BaseNum;
    }

    public int GetHorescopeNum()
    {
    	return GetHoroscopeNum(SolarDate.Month,SolarDate.Day,1);
    }

    //====================================传入公历月、天、基本数，传回对应公历的星座中文名称
    public String GetHoroscopeCnName(int m,int d,int BaseNum)
    {
    	return cnHoroscope[GetHoroscopeNum(m,d,BaseNum)-BaseNum];
    }
    
    public String GetHoroscopeCnName()
    {
    	return cnHoroscope[GetHoroscopeNum(SolarDate.Month,SolarDate.Day,0)-0];
    }
    public Date GetSolar()
    {
    	Date dt=new Date();
    	dt.setYear(SolarDate.Year-1900);
    	dt.setMonth(SolarDate.Month-1);
    	dt.setDate(SolarDate.Day);
    	dt.setHours(0);
    	dt.setMinutes(0);
    	dt.setSeconds(0);
    	return dt;
    }
    public Date GetSolar(int _Year,int _Month,int _Day,int _DayType)
    {
    	LSDate dSolar=null;
    	boolean bGetSolarDate=false;
    	boolean blValid=false;
    	int iCDay=_Day;
    	switch(_DayType)
    	{
	    	case 1:
	    		if(_Month==2&&_Day==29&&!isSolarLeapYear(_Year))
	    		{
	    			iCDay=28;
	    		}
	    		dSolar=new LSDate(_Year,_Month,iCDay,false);
	    		bGetSolarDate=true;
	    		break;
	    	case 2:
	    		dSolar=Lunar2Solar(_Year,_Month,_Day,false);
	    		bGetSolarDate=true;
	    		break;
	    	case 3:
	    		blValid=GetStatus(_Year,_Month,_Day,3);
	    		if(blValid)
	    		{
	    			dSolar=Lunar2Solar(_Year,_Month,_Day,true);
	    			bGetSolarDate=true;
	    		}
	    		else
	    		{
	    			if(_Day==30)
	    			{
	    				iCDay=_Day-1;
	    				blValid=GetStatus(_Year,_Month,iCDay,3);
	    				if(blValid)
	    				{
	    					dSolar=Lunar2Solar(_Year,_Month,iCDay,true);
	    					bGetSolarDate=true;
	    				}
	    				else
	    				{
	    					dSolar=Lunar2Solar(_Year,_Month,_Day,false);
	    					bGetSolarDate=true;
	    				}
	    			}
	    			else
	    			{
	    				dSolar=Lunar2Solar(_Year,_Month,_Day,false);
	    				bGetSolarDate=true;
	    			}
	    		}
	    		break;
	    	case 4:
	    		dSolar=new LSDate(_Year,_Month,_Day,false);			
				int iWeek;
				int iWeekDay;
				int mfWeekDay;
				int monthDays;
				int cDay;
				iWeek=(int)_Day/10;
				iWeekDay=(int)_Day%10;
				if(iWeek==5)
				{
					monthDays=SolarMonthDays(_Year,_Month);
					mfWeekDay=CaculateWeekDay(_Year,_Month,monthDays);
					if(mfWeekDay>=iWeekDay)
					{
						cDay=monthDays-mfWeekDay+iWeekDay;
					}
					else
					{
						cDay=monthDays-mfWeekDay+(iWeekDay-7);
					}
				}
				else
				{
					int iWeekNum=0;
					mfWeekDay=CaculateWeekDay(_Year,_Month,1);
					if(mfWeekDay<=iWeekDay)
					{
						iWeekNum=1;
					}
					cDay=(iWeek-iWeekNum)*7-mfWeekDay+iWeekDay+1;
				}
				dSolar.Day		= cDay;
				bGetSolarDate=true;
				break;
	    	case 5:
	    		dSolar=GetSoralTermDate(_Year,_Day);
	    		bGetSolarDate=true;
				break;
	    	default:
	    		bGetSolarDate=false;
	    		break;
    	}
    	bValid=bGetSolarDate;
    	Date dt=new Date();
    	if(bValid)
    	{
    		dt.setTime(0);
	    	dt.setYear(dSolar.Year-1900);
	    	dt.setMonth(dSolar.Month-1);
	    	dt.setDate(dSolar.Day);
	    	dt.setHours(23);
	    	dt.setMinutes(59);
	    	dt.setSeconds(59);
	    	
    	}
    	return dt;
    }

    public String GetCurYearSolarString(int _GetType)
    {
    	String sSolarString="";
    	Date dSolar=new Date();
    	int iYear=dSolar.getYear()+1900;
    	bValid=true;
    	switch(_GetType)
		{
			case 1:
				dSolar=GetSolar(iYear,SolarDate.Month,SolarDate.Day,1);
				break;
			case 2:
				dSolar=GetSolar(iYear,LunarDate.Month,LunarDate.Day,2);
				break;
			case 3:
				if(GetStatus(iYear,LunarDate.Month,LunarDate.Day,3))
				{
					dSolar=GetSolar(iYear,LunarDate.Month,LunarDate.Day,3);
				}
				else
				{
					bValid=false;
				}
				break;
			case 4:
				dSolar=GetSolar(iYear,SolarDate.Month,SolarDate.Day,4);
				break;
			case 5:
				dSolar=GetSolar(iYear,SolarDate.Month,SolarDate.Day,5);
				break;
		}
    	if(bValid)
		{
    		sSolarString=String.format("%d年%02d月%02d日 ",dSolar.getYear()+1900,dSolar.getMonth()+1,dSolar.getDate())+GetCnWeekDay(dSolar.getDay());
    		//sSolarString=String.valueOf(dSolar.getYear()+1900)+"年"+String.valueOf(dSolar.getMonth())+"月"+String.valueOf(dSolar.getDate())+"日 "+CaculateWeekDayC(dSolar.getYear()+1900,dSolar.getMonth(),dSolar.getDate());
		}
    	return sSolarString;
    }

    public String GetNxtYearSolarString(int _GetType)
    {
    	String sSolarString="";
    	Date dSolar=new Date();
    	int iYear=dSolar.getYear()+1900+1;
    	bValid=true;
    	switch(_GetType)
		{
			case 1:
				dSolar=GetSolar(iYear,SolarDate.Month,SolarDate.Day,1);
				break;
			case 2:
				dSolar=GetSolar(iYear,LunarDate.Month,LunarDate.Day,2);
				break;
			case 3:
				if(GetStatus(iYear,LunarDate.Month,LunarDate.Day,3))
				{
					dSolar=GetSolar(iYear,LunarDate.Month,LunarDate.Day,3);
				}
				else
				{
					bValid=false;
				}
				break;
			case 4:
				dSolar=GetSolar(iYear,SolarDate.Month,SolarDate.Day,4);
				break;
			case 5:
				dSolar=GetSolar(iYear,SolarDate.Month,SolarDate.Day,5);
				break;
		}
    	if(bValid)
		{
    		sSolarString=String.format("%d年%02d月%02d日 ",dSolar.getYear()+1900,dSolar.getMonth()+1,dSolar.getDate())+GetCnWeekDay(dSolar.getDay());
    		//sSolarString=String.valueOf(dSolar.getYear()+1900)+"年"+String.valueOf(dSolar.getMonth())+"月"+String.valueOf(dSolar.getDate())+"日 "+CaculateWeekDayC(dSolar.getYear(),dSolar.getMonth(),dSolar.getDate());
		}
    	return sSolarString;
    }

    private Date GetNextDateSolar(int _Year,int _RemindType)
    {
    	//RemindType------1:solar;2:lunar;4:lunarleap;
    	boolean bExist=false;
    	Date dSolar=new Date();
    	Date dCurr=new Date();
    	Date dTemp=new Date();
    	
    	if(_RemindType % 2 ==1)
    	{
    		dTemp=GetSolar(_Year,SolarDate.Month,SolarDate.Day,1);
    		if(d1Afterd2(dCurr,dTemp))
    		{
    			dTemp=GetNextDateSolar(_Year+1,1);
    		}
    		dSolar=dTemp;
    		bExist=true;
    	}
    	if(((int)(_RemindType / 2))%2==1)
    	{
    		dTemp=GetSolar(_Year,LunarDate.Month,LunarDate.Day,2);
    		if(d1Afterd2(dCurr,dTemp))
    		{
    			dTemp=GetNextDateSolar(_Year+1,2);
    		}
    		if(bExist)
    		{
    			if(d1Afterd2(dSolar,dTemp))
    			{
    				dSolar=dTemp;
    			}
    		}
    		else
    		{
    			dSolar=dTemp;
    		}
    		bExist=true;
    	}
    	if((int)(_RemindType / 4) == 1)
    	{
    		dTemp=GetSolar(_Year,LunarDate.Month,LunarDate.Day,3);
    		if(d1Afterd2(dCurr,dTemp))
    		{
    			dTemp=GetNextDateSolar(_Year+1,4);
    		}
    		if(bValid)
    		{
	    		if(bExist)
	    		{
	    			if(d1Afterd2(dSolar,dTemp))
	    			{
	    				dSolar=dTemp;
	    			}
	    		}
	    		else
	    		{
		    		dSolar=dTemp;
	    		}
    		}
    		else
    		{
    			dTemp=GetSolar(_Year,LunarDate.Month,LunarDate.Day,2);
    			if(d1Afterd2(dCurr,dTemp))
        		{
        			dTemp=GetNextDateSolar(_Year+1,2);
        		}
    			if(bExist)
	    		{
	    			if(d1Afterd2(dSolar,dTemp))
	    			{
	    				dSolar=dTemp;
	    			}
	    		}
	    		else
	    		{
		    		dSolar=dTemp;
	    		}
    		}
    		bExist=true;
    	}
    	if(_RemindType == 8)
    	{
    		dTemp=GetSolar(_Year,SolarDate.Month,SolarDate.Day,4);
    		if(d1Afterd2(dCurr,dTemp))
    		{
    			dTemp=GetNextDateSolar(_Year+1,8);
    		}
    		dSolar=dTemp;
    		bExist=true;
    	}
    	if(_RemindType==16)
    	{
    		dTemp=GetSolar(_Year,SolarDate.Month,SolarDate.Day,5);
    		if(d1Afterd2(dCurr,dTemp))
    		{
    			dTemp=GetNextDateSolar(_Year+1,16);
    		}
    		dSolar=dTemp;
    		bExist=true;
    	}
    	bValid=true;
    	return dSolar;
    }

    public Date GetNextDateSolar(int _RemindType)
    {
    	Date dSolar=new Date();
    	int iYear=dSolar.getYear()+1900;
    	dSolar=GetNextDateSolar(iYear,_RemindType);
    	return dSolar;
    }

    public String GetNextDateSolarString(int _RemindType)
    {
    	String sSolarString="";
    	Date dSolar=GetNextDateSolar(_RemindType);
    	if(bValid)
    	{
    		sSolarString=String.format("%d年%02d月%02d日 ",dSolar.getYear()+1900,dSolar.getMonth()+1,dSolar.getDate())+GetCnWeekDay(dSolar.getDay());
    		//sSolarString=String.valueOf(dSolar.getYear()+1900)+"年"+String.valueOf(dSolar.getMonth())+"月"+String.valueOf(dSolar.getDate())+"日 "+CaculateWeekDayC(dSolar.getYear(),dSolar.getMonth(),dSolar.getDate());
    	}
    	else
    	{
    		sSolarString="无法获取临近日期";
    	}
    	
    	return sSolarString;
    }
    
    public String getChinaWeekdayString(String weekday)
    { 
     if(weekday.equals("Mon")) 
      return "一"; 
     if(weekday.equals("Tue")) 
      return "二"; 
     if(weekday.equals("Wed")) 
      return "三"; 
     if(weekday.equals("Thu")) 
      return "四"; 
     if(weekday.equals("Fri")) 
      return "五"; 
     if(weekday.equals("Sat")) 
      return "六"; 
     if(weekday.equals("Sun")) 
      return "日"; 
     else 
      return ""; 
    } 
    
    public int GetFtvLength(int FtvType)
    {
    	switch(FtvType)
    	{
	    	case 1:
	    		return sFtv.length;
	    	case 2:
	    		return lFtv.length;
	    	case 4:
	    		return wFtv.length;
    		default:
    			return 0;
    	}
    }
    public int GetFtvMonth(int FtvType,int Index)
    {
    	String[] Ftv;
    	switch(FtvType)
    	{
    	case 1:
    		Ftv=sFtv;
    		break;
    	case 2:
    		Ftv=lFtv;
    		break;
    	case 4:
    		Ftv=wFtv;
    		break;
    	default:
    		return 0;
    	}
    	return Integer.parseInt(Ftv[Index].substring(0, 2));
    }
    public int GetFtvDay(int FtvType,int Index)
    {
    	String[] Ftv;
    	switch(FtvType)
    	{
    	case 1:
    		Ftv=sFtv;
    		break;
    	case 2:
    		Ftv=lFtv;
    		break;
    	case 4:
    		Ftv=wFtv;
    		break;
    	default:
    		return 0;
    	}
    	return Integer.parseInt(Ftv[Index].substring(2, 4));
    }
    public int GetFtvId(int FtvType,int Index)
    {
    	String[] Ftv;
    	switch(FtvType)
    	{
    	case 1:
    		Ftv=sFtv;
    		break;
    	case 2:
    		Ftv=lFtv;
    		break;
    	case 4:
    		Ftv=wFtv;
    		break;
    	default:
    		return 0;
    	}
    	return FtvType*10000+Integer.parseInt(Ftv[Index].substring(0, 4));
    }
    public String GetFtvName(int FtvType,int Index)
    {
    	String[] Ftv;
    	switch(FtvType)
    	{
    	case 1:
    		Ftv=sFtv;
    		break;
    	case 2:
    		Ftv=lFtv;
    		break;
    	case 4:
    		Ftv=wFtv;
    		break;
    	default:
    		return "";
    	}
    	return Ftv[Index].substring(5, Ftv[Index].length());
    }
    public String getFtvDesp(int FtvType,int Index)
    {
    	String[] Ftv;
    	switch(FtvType)
    	{
    	case 1:
    		Ftv=sFtv;
    		break;
    	case 2:
    		Ftv=lFtv;
    		break;
    	case 4:
    		Ftv=wFtv;
    		break;
    	default:
    		return "";
    	}
    	String sMonth= Ftv[Index].substring(0, 2);
    	String sDay= Ftv[Index].substring(2, 4);
    	String strFtvDesp="";
    	switch(FtvType)
    	{
    	case 1:
    		strFtvDesp=sMonth+"月"+sDay+"日";
    		break;
    	case 2:
    		strFtvDesp=LunarMonth[Integer.parseInt(sMonth)-1]+"月"+GetLunarDayString(Integer.parseInt(sDay));
    		break;
    	case 4:
    		strFtvDesp=sMonth+"月第";
    		int iWeek=Integer.parseInt(sDay.substring(0,1));
    		int iWeekDay=Integer.parseInt(sDay.substring(1,2));
    		if(iWeek==5)
    		{
    			strFtvDesp+="最后一个星期"+weekDayName[iWeekDay];
    		}
    		else
    		{
    			strFtvDesp+=chiaNum[iWeek]+"个星期"+weekDayName[iWeekDay];
    		}
    		break;
    	}
    	Ftv=null;
    	return strFtvDesp;
    }
//  ===== y年的第n个节气为几日(从0小寒起算)
    private int sTerm(int y, int n) 
    {
    	Date dt=new Date(2001, 0, 5, 14, 49, 0);
    	long lTemp = dt.getTime();
    	dt=new Date((long)(31556925974.7 * (y - 2001)+sTermInfo[n-1]*60000.0+lTemp));
    	return dt.getDate();
    }
    public String GetSoralTerm(int y, int m, int d)
    {
    	String solarTerms;
    	if (d == sTerm(y, m * 2 - 1)) 
    		solarTerms = solarTerm[(m - 1) * 2];
    	else if (d == sTerm(y, m  * 2 )) 
    		solarTerms = solarTerm[(m - 1 )* 2 + 1];
    	else
    	{
    		solarTerms = "";
    	}
    		return  solarTerms;
    }
    public String  GetSoralTerm(Date date)
    {
    	return GetSoralTerm(date.getYear()+1900, date.getMonth()+1, date.getDate());
    
    }
    public String  GetSoralTerm()
    {
    	return GetSoralTerm(SolarDate.Year, SolarDate.Month, SolarDate.Day);
    
    }
    public LSDate GetSoralTermDate(int y,int n)
    {
    	int m=(int)((n+1)/2);
    	int d=sTerm(y, n);
    	LSDate dSolar=new LSDate(y,m,d,false);
    	return dSolar;
    }
    public LSDate GetSoralTermDate(int n)
    {
    	LSDate dSolar=new LSDate();
    	Date dt=new Date();
    	
    	dSolar=GetSoralTermDate(dt.getYear()+1900,n);
    	return dSolar;
    }
    public int GetSoralTermId(int Index)
    {
    	return 5*10000+Index+1;
    }
    public String GetSoralTermCnName(int Index)
    {
    	return solarTerm[Index];
    }
    public String GetSoralTermCnDesp(int Index)
    {
    	return solarTermDsp[Index];
    }
    
    public String GetLunarCnMonthDayName(int _Month,int _Day,boolean isLeap)
	{
		String sCnDayName="";
		if(isLeap)
		{
			sCnDayName+="润";
		}
		sCnDayName+=LunarMonth[_Month-1]+"月　";
		sCnDayName+=GetLunarDayString(_Day);
		return sCnDayName;	
	}
    public String GetLunarCnMonthName()
	{
		String sCnMonthName="";
		if(LunarDate.IsLeap)
		{
			sCnMonthName+="润";
		}
		sCnMonthName+=LunarMonth[LunarDate.Month-1];
		return sCnMonthName;	
	}
    public String GetLunarCnDayName()
	{
		return GetLunarDayString(LunarDate.Day);	
	}
    public String GetLunarCnMonthDayName()
	{
		String sCnDayName="";
		if(LunarDate.IsLeap)
		{
			sCnDayName+="润";
		}
		sCnDayName+=LunarMonth[LunarDate.Month-1]+"月　";
		sCnDayName+=GetLunarDayString(LunarDate.Day);
		return sCnDayName;	
	}
    
    final public String				GetGanZhi(Date _CurrDate)
    {
    	String sTmp="";
    	//取得当前年立春 的时间
    	LSDate lsDt=GetSoralTermDate(_CurrDate.getYear()+1900,3);
    	Date dt=new Date(lsDt.Year-1900,lsDt.Month-1,lsDt.Day,0,0,0);
    	int iDays=(int)((_CurrDate.getTime()-dt.getTime())/(24*60*60*1000));
    	if(iDays<0)
    	{
    		lsDt=GetSoralTermDate(_CurrDate.getYear()+1900-1,3);
    		dt=new Date(lsDt.Year-1900,lsDt.Month-1,lsDt.Day);
    		iDays=(int)((_CurrDate.getTime()-dt.getTime())/(24*60*60*1000));
    	}
    	int iYear=lsDt.Year;
    	int num=lsDt.Year-1900+36;
    	int iYearGan=num%10;
    	int iOneMonthGan=0;
    	if(iYearGan==0||iYearGan==1)
    	{
    		iOneMonthGan=2;
    	}
    	else if(iYearGan==5||iYearGan==6)
    	{
    		iOneMonthGan=5;
    	}else if(iYearGan==2||iYearGan==7)
    	{
    		iOneMonthGan=6;
    	}else if(iYearGan==3||iYearGan==8)
    	{
    		iOneMonthGan=8;
    	}else if(iYearGan==4||iYearGan==9)
    	{
    		iOneMonthGan=0;
    	}
    	int iOneMonthZhi=2;
    	int iMonth=_CurrDate.getMonth()+1;
    	//取得当前月的节气
    	lsDt=GetSoralTermDate(iYear,iMonth*2-1);
    	if(_CurrDate.getDate()<lsDt.Day)
    	{
    		iMonth--;
    		if(iMonth==0)
    		{
    			iMonth=12;
    		}
    	}
    	iMonth--;
    	if(iMonth==0)
    	{
    		iMonth=12;
    	}
    	//取得当前月的干支
    	int iMonthGan=(iOneMonthGan+iMonth-1)%10;
    	int iMonthZhi=(iOneMonthZhi+iMonth-1)%12;
    	int iDayC = (int)(_CurrDate.getTime()/86400000.0+25567+11);

    	sTmp=("["+GetAnimalsYear(iYear)+"]"+Gan[num % 10] + Zhi[num % 12])+"年 "+(Gan[iMonthGan] + Zhi[iMonthZhi])+"月 "+(Gan[iDayC%10] + Zhi[iDayC%12])+"日";
    	return sTmp;
    }
    final public String				GetGanZhi()
    {
    	String sTmp="";
    	//取得当前年立春 的时间
    	Date CurrDate=new Date(SolarDate.Year-1900,SolarDate.Month-1,SolarDate.Day);
    	LSDate lsDt=GetSoralTermDate(SolarDate.Year,3);
    	Date dt=new Date(lsDt.Year-1900,lsDt.Month-1,lsDt.Day);
    	int iDays=(int)((CurrDate.getTime()-dt.getTime())/(24*60*60*1000));
    	if(iDays<0)
    	{
    		lsDt=GetSoralTermDate(SolarDate.Year-1,3);
    		dt=new Date(lsDt.Year-1900,lsDt.Month-1,lsDt.Day);
    		iDays=(int)((CurrDate.getTime()-dt.getTime())/(24*60*60*1000));
    	}
    	int iYear=lsDt.Year;
    	int num=lsDt.Year-1900+36;
    	int iYearGan=num%10;
    	int iOneMonthGan=0;
    	if(iYearGan==0||iYearGan==1)
    	{
    		iOneMonthGan=2;
    	}
    	else if(iYearGan==5||iYearGan==6)
    	{
    		iOneMonthGan=5;
    	}else if(iYearGan==2||iYearGan==7)
    	{
    		iOneMonthGan=6;
    	}else if(iYearGan==3||iYearGan==8)
    	{
    		iOneMonthGan=8;
    	}else if(iYearGan==4||iYearGan==9)
    	{
    		iOneMonthGan=0;
    	}
    	int iOneMonthZhi=2;
    	
    	//取得当前月的节气
    	int iMonth=CurrDate.getMonth()+1;
    	lsDt=GetSoralTermDate(iYear,iMonth*2-1);
    	if(CurrDate.getDate()<lsDt.Day)
    	{
    		iMonth--;
    		if(iMonth==0)
    		{
    			iMonth=12;
    		}
    	}
    	iMonth--;
    	if(iMonth==0)
    	{
    		iMonth=12;
    	}
    	//取得当前月的干支
    	int iMonthGan=(iOneMonthGan+iMonth-1)%10;
    	int iMonthZhi=(iOneMonthZhi+iMonth-1)%12;
    	int iDayC = (int)(CurrDate.getTime()/86400000.0+25567+11);

    	sTmp=(Gan[num % 10] + Zhi[num % 12])+"年 "+(Gan[iMonthGan] + Zhi[iMonthZhi])+"月 "+(Gan[iDayC%10] + Zhi[iDayC%12])+"日";
    	return sTmp;
    }
} 