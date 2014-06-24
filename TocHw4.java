/* 資訊104 F74004020 呂則慷 HW4
   簡單說明：一開始把URL的資訊抓下來，存成json格式，我使用RE分成四種情形分別處理(大道，、路、街、巷)，然後又分別用一些
   一維、二維陣列去儲存"土地區段位置或建物區門牌"、"交易年月"、"總價元"這些資訊，整個程式用一個for迴圈貫串，
   如果陣列中沒有該路之各項資訊，就把該之各項資訊儲存進陣列，若for迴圈掃到該路之"交易年月"在先前的陣列中已經儲存，
   則不需要再增加他的交易年月次數，若是"交易年月"在先前並沒出現，表示是一筆新的交易，則交易次數加一，分別將大道、路、
   街、巷處理完之後，開始針對我所儲存的陣列中的資訊，找出"交易年月"次數最多的該筆資料，在我陣列中的哪一個位置，
   若有兩筆資料交易年月次數同是最大，則將兩筆資訊的位置都找出來，最後印出所有結果，結束程式。
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONArray;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TocHw4 {
    private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONArray json = new JSONArray(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

public static void main(String[] args) throws IOException, JSONException {
    JSONArray json = readJsonFromUrl(args[0]);
    int i,j=0;
    int y,z;
    int A=json.length();
    String [] LoadName= new String[A];
    int [][] BuyYear= new int[A][1000];
    int [][] BuyMoney= new int[A][1000];
    int [] YearFlag=new int[A];
    int [] MoneyFlag=new int[A];
    int StartFlag=0;
    int tag=0;
    Pattern pattern1=Pattern.compile(".*大道");
    Pattern pattern2=Pattern.compile(".*路");
    Pattern pattern3=Pattern.compile(".*街");
    Pattern pattern4=Pattern.compile(".*巷");
    for(i=0;i<=A-1;i++)
    {
    	Matcher matcher1=pattern1.matcher(json.getJSONObject(i).getString("土地區段位置或建物區門牌"));
    	Matcher matcher2=pattern2.matcher(json.getJSONObject(i).getString("土地區段位置或建物區門牌"));
    	Matcher matcher3=pattern3.matcher(json.getJSONObject(i).getString("土地區段位置或建物區門牌"));
    	Matcher matcher4=pattern4.matcher(json.getJSONObject(i).getString("土地區段位置或建物區門牌"));
    	if(matcher1.find())
    	{
    		if(StartFlag==0)
    		{
    			LoadName[tag]=matcher1.group();
    			BuyYear[tag][YearFlag[tag]]=json.getJSONObject(i).getInt("交易年月");
	    		YearFlag[tag]++;
	    		BuyMoney[tag][MoneyFlag[tag]]=json.getJSONObject(i).getInt("總價元");
	    		MoneyFlag[tag]++;
    			StartFlag=1;
    			tag++;
    		}
    		else
    		{
	    		for(y=tag-1;y>=0;y--)
	    		{
	    			if(matcher1.group().equals(LoadName[y]))
	    			{
	    				for(z=YearFlag[y];z>=0;z--)
	    				{	
			    				if(json.getJSONObject(i).getInt("交易年月")==BuyYear[y][z])
			    				{
			    					BuyMoney[y][MoneyFlag[y]]=json.getJSONObject(i).getInt("總價元");
			    		    			MoneyFlag[y]++;
			    		    			break;
			    				}
			    				else if(z==0)
			    				{
			    					BuyYear[y][YearFlag[y]]=json.getJSONObject(i).getInt("交易年月");
			    		    			YearFlag[y]++;
			    		    			BuyMoney[y][MoneyFlag[y]]=json.getJSONObject(i).getInt("總價元");
			    		    			MoneyFlag[y]++;
			    				}
	    				}
	    				break;
	    			}
	    			else if(y==0)
	    			{
	    				LoadName[tag]=matcher1.group();
	    				BuyYear[tag][YearFlag[tag]]=json.getJSONObject(i).getInt("交易年月");
			    		YearFlag[tag]++;
			    		BuyMoney[tag][MoneyFlag[tag]]=json.getJSONObject(i).getInt("總價元");
			    		MoneyFlag[tag]++;
			    		tag++;
	    			}
	    		}
    		}
    	}
    	else if(matcher2.find())
    	{	
    		if(StartFlag==0)
    		{
    			LoadName[tag]=matcher2.group();
    			BuyYear[tag][YearFlag[tag]]=json.getJSONObject(i).getInt("交易年月");
	    		YearFlag[tag]++;
	    		BuyMoney[tag][MoneyFlag[tag]]=json.getJSONObject(i).getInt("總價元");
	    		MoneyFlag[tag]++;
    			StartFlag=1;
    			tag++;
    		}
    		else
    		{
	    		for(y=tag-1;y>=0;y--)
	    		{
	    			if(matcher2.group().equals(LoadName[y]))
	    			{
	    				for(z=YearFlag[y];z>=0;z--)
	    				{	
			    				if(json.getJSONObject(i).getInt("交易年月")==BuyYear[y][z])
			    				{
			    					BuyMoney[y][MoneyFlag[y]]=json.getJSONObject(i).getInt("總價元");
			    		    			MoneyFlag[y]++;
			    		    			break;
			    				}
			    				else if(z==0)
			    				{
			    					BuyYear[y][YearFlag[y]]=json.getJSONObject(i).getInt("交易年月");
			    		    			YearFlag[y]++;
			    		    			BuyMoney[y][MoneyFlag[y]]=json.getJSONObject(i).getInt("總價元");
			    		    			MoneyFlag[y]++;
			    				}
	    				}
	    				break;
	    			}
	    			else if(y==0)
	    			{
	    				LoadName[tag]=matcher2.group();
	    				BuyYear[tag][YearFlag[tag]]=json.getJSONObject(i).getInt("交易年月");
			    		YearFlag[tag]++;
			    		BuyMoney[tag][MoneyFlag[tag]]=json.getJSONObject(i).getInt("總價元");
			    		MoneyFlag[tag]++;
			    		tag++;
	    			}
	    		}
    		}
    	}
    	else if(matcher3.find())
    	{
    		if(StartFlag==0)
    		{
    			LoadName[tag]=matcher3.group();
    			BuyYear[tag][YearFlag[tag]]=json.getJSONObject(i).getInt("交易年月");
	    		YearFlag[tag]++;
	    		BuyMoney[tag][MoneyFlag[tag]]=json.getJSONObject(i).getInt("總價元");
	    		MoneyFlag[tag]++;
    			StartFlag=1;
    			tag++;
    		}
    		else
    		{
	    		for(y=tag-1;y>=0;y--)
	    		{
	    			if(matcher3.group().equals(LoadName[y]))
	    			{
	    				for(z=YearFlag[y];z>=0;z--)
	    				{	
			    				if(json.getJSONObject(i).getInt("交易年月")==BuyYear[y][z])
			    				{
			    					BuyMoney[y][MoneyFlag[y]]=json.getJSONObject(i).getInt("總價元");
			    		    			MoneyFlag[y]++;
			    		    			break;
			    				}
			    				else if(z==0)
			    				{
			    					BuyYear[y][YearFlag[y]]=json.getJSONObject(i).getInt("交易年月");
			    		    			YearFlag[y]++;
			    		    			BuyMoney[y][MoneyFlag[y]]=json.getJSONObject(i).getInt("總價元");
			    		    			MoneyFlag[y]++;
			    				}
	    				}
	    				break;
	    			}
	    			else if(y==0)
	    			{
	    				LoadName[tag]=matcher3.group();
	    				BuyYear[tag][YearFlag[tag]]=json.getJSONObject(i).getInt("交易年月");
			    		YearFlag[tag]++;
			    		BuyMoney[tag][MoneyFlag[tag]]=json.getJSONObject(i).getInt("總價元");
			    		MoneyFlag[tag]++;
			    		tag++;
	    			}
	    		}
    		}
    	}
    	else if(matcher4.find())
    	{
    		if(StartFlag==0)
    		{
    			LoadName[tag]=matcher4.group();
    			BuyYear[tag][YearFlag[tag]]=json.getJSONObject(i).getInt("交易年月");
	    		YearFlag[tag]++;
	    		BuyMoney[tag][MoneyFlag[tag]]=json.getJSONObject(i).getInt("總價元");
	    		MoneyFlag[tag]++;
    			StartFlag=1;
    			tag++;
    		}
    		else
    		{
	    		for(y=tag-1;y>=0;y--)
	    		{
	    			if(matcher4.group().equals(LoadName[y]))
	    			{
	    				for(z=YearFlag[y];z>=0;z--)
	    				{	
			    				if(json.getJSONObject(i).getInt("交易年月")==BuyYear[y][z])
			    				{
			    					BuyMoney[y][MoneyFlag[y]]=json.getJSONObject(i).getInt("總價元");
			    		    			MoneyFlag[y]++;
			    		    			break;
			    				}
			    				else if(z==0)
			    				{
			    					BuyYear[y][YearFlag[y]]=json.getJSONObject(i).getInt("交易年月");
			    		    			YearFlag[y]++;
			    		    			BuyMoney[y][MoneyFlag[y]]=json.getJSONObject(i).getInt("總價元");
			    		    			MoneyFlag[y]++;
			    				}
	    				}
	    				break;
	    			}
	    			else if(y==0)
	    			{
	    				LoadName[tag]=matcher4.group();
	    				BuyYear[tag][YearFlag[tag]]=json.getJSONObject(i).getInt("交易年月");
			    		YearFlag[tag]++;
			    		BuyMoney[tag][MoneyFlag[tag]]=json.getJSONObject(i).getInt("總價元");
			    		MoneyFlag[tag]++;
			    		tag++;
	    			}
	    		}
    		}
    	}
    }
    int max_distinct,NumOfRoad=1;
    int [] max =new int[10];
    int [] min =new int[10];
    int [] tmp =new int[10];
    max_distinct=YearFlag[0];
    for(i=1;i<=A-1;i++)
    {
    	if(max_distinct<YearFlag[i])
    	{
    		max_distinct=YearFlag[i];
    		tmp[0]=i;
    	}
    }
    for(i=1;i<=A-1;i++)
    {
    	if(max_distinct==YearFlag[i]&&tmp[0]!=i)
    	{
    		tmp[NumOfRoad]=i;
    		NumOfRoad++;
    	}
    }
    for(i=0;i<NumOfRoad;i++)
    {
        max[i]=BuyMoney[tmp[i]][0];
        min[i]=BuyMoney[tmp[i]][0];
    }
    for(j=0;j<NumOfRoad;j++)
    {
	    for(i=1;i<=MoneyFlag[tmp[j]]-1;i++)
	    {
		    if(max[j]<=BuyMoney[tmp[j]][i])
		    max[j]=BuyMoney[tmp[j]][i];
		    	
		    if(min[j]>=BuyMoney[tmp[j]][i])
		        min[j]=BuyMoney[tmp[j]][i];
	    }
    	
    }
    for(i=0;i<NumOfRoad;i++)
    {
    	System.out.println(LoadName[tmp[i]]+",最高成交價:"+max[i]+",最低成交價:"+min[i]);
    }
  } 
}
