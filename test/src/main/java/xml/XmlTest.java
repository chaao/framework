package xml;

import baseFramework.xml.XMLObject;
import baseFramework.xml.XMLParser;
import org.dom4j.DocumentException;

import java.net.MalformedURLException;

/**
 * @author chao.li
 * @date 2017/11/15
 */
public class XmlTest {
	public static void main(String[] args) throws MalformedURLException, DocumentException {
		String content = "<?xml version='1.0'?><Report reportTimeStamp='2017-11-15T11:08:13.986+08:00'><HeaderData/><ReportData><ColumnHeaders><ColumnHeader name='ADX_TAG_CODE' localizedName='Tag code'/><ColumnHeader name='ADX_MATCHED_QUERIES' localizedName='Matched requests'/><ColumnHeader name='ADX_COVERAGE' localizedName='Coverage'/><ColumnHeader name='ADX_REQUESTS' localizedName='Ad requests'/><ColumnHeader name='ADX_AD_IMPRESSIONS_ADJUSTED' localizedName='Ad impressions'/><ColumnHeader name='ADX_CLICKS' localizedName='Clicks'/><ColumnHeader name='ADX_ESTIMATED_REVENUE' localizedName='Estimated revenue'/></ColumnHeaders><DataSet><Row rowNum='1'><Column name='ADX_TAG_CODE'><Val></Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>408</Val></Column><Column name='ADX_COVERAGE'><Val>78.01%</Val></Column><Column name='ADX_REQUESTS'><Val>523</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>358</Val></Column><Column name='ADX_CLICKS'><Val>1</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$0.07</Val></Column></Row><Row rowNum='2'><Column name='ADX_TAG_CODE'><Val>1107703401</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>76,273</Val></Column><Column name='ADX_COVERAGE'><Val>15.29%</Val></Column><Column name='ADX_REQUESTS'><Val>498,858</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>10,614</Val></Column><Column name='ADX_CLICKS'><Val>138</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$15.12</Val></Column></Row><Row rowNum='3'><Column name='ADX_TAG_CODE'><Val>1416004138</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>61,139</Val></Column><Column name='ADX_COVERAGE'><Val>42.86%</Val></Column><Column name='ADX_REQUESTS'><Val>142,659</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>11,281</Val></Column><Column name='ADX_CLICKS'><Val>144</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$4.99</Val></Column></Row><Row rowNum='4'><Column name='ADX_TAG_CODE'><Val>1466063097</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>1,808,898</Val></Column><Column name='ADX_COVERAGE'><Val>50.36%</Val></Column><Column name='ADX_REQUESTS'><Val>3,592,069</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>59,762</Val></Column><Column name='ADX_CLICKS'><Val>1,034</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$36.24</Val></Column></Row><Row rowNum='5'><Column name='ADX_TAG_CODE'><Val>1485147218</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>182,207</Val></Column><Column name='ADX_COVERAGE'><Val>13.76%</Val></Column><Column name='ADX_REQUESTS'><Val>1,324,285</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>31,943</Val></Column><Column name='ADX_CLICKS'><Val>466</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$22.96</Val></Column></Row><Row rowNum='6'><Column name='ADX_TAG_CODE'><Val>1722636441</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>16,581</Val></Column><Column name='ADX_COVERAGE'><Val>12.50%</Val></Column><Column name='ADX_REQUESTS'><Val>132,610</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>7,278</Val></Column><Column name='ADX_CLICKS'><Val>54</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$9.78</Val></Column></Row><Row rowNum='7'><Column name='ADX_TAG_CODE'><Val>2341939761</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>20</Val></Column><Column name='ADX_COVERAGE'><Val>100.00%</Val></Column><Column name='ADX_REQUESTS'><Val>20</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>1</Val></Column><Column name='ADX_CLICKS'><Val>0</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$0.00</Val></Column></Row><Row rowNum='8'><Column name='ADX_TAG_CODE'><Val>2456149435</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>9,996</Val></Column><Column name='ADX_COVERAGE'><Val>22.40%</Val></Column><Column name='ADX_REQUESTS'><Val>44,626</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>9,843</Val></Column><Column name='ADX_CLICKS'><Val>44</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$3.55</Val></Column></Row><Row rowNum='9'><Column name='ADX_TAG_CODE'><Val>2547074702</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>656,775</Val></Column><Column name='ADX_COVERAGE'><Val>12.03%</Val></Column><Column name='ADX_REQUESTS'><Val>5,458,503</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>440,206</Val></Column><Column name='ADX_CLICKS'><Val>6,585</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$692.06</Val></Column></Row><Row rowNum='10'><Column name='ADX_TAG_CODE'><Val>2584411281</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>534,856</Val></Column><Column name='ADX_COVERAGE'><Val>24.09%</Val></Column><Column name='ADX_REQUESTS'><Val>2,220,257</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>215,876</Val></Column><Column name='ADX_CLICKS'><Val>1,922</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$149.84</Val></Column></Row><Row rowNum='11'><Column name='ADX_TAG_CODE'><Val>2662479320</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>90,580</Val></Column><Column name='ADX_COVERAGE'><Val>28.40%</Val></Column><Column name='ADX_REQUESTS'><Val>318,957</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>21,040</Val></Column><Column name='ADX_CLICKS'><Val>328</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$16.59</Val></Column></Row><Row rowNum='12'><Column name='ADX_TAG_CODE'><Val>2681794900</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>77,439</Val></Column><Column name='ADX_COVERAGE'><Val>54.72%</Val></Column><Column name='ADX_REQUESTS'><Val>141,513</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>5,142</Val></Column><Column name='ADX_CLICKS'><Val>158</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$49.04</Val></Column></Row><Row rowNum='13'><Column name='ADX_TAG_CODE'><Val>2717766876</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>25,448</Val></Column><Column name='ADX_COVERAGE'><Val>13.75%</Val></Column><Column name='ADX_REQUESTS'><Val>185,086</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>24,002</Val></Column><Column name='ADX_CLICKS'><Val>197</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$22.58</Val></Column></Row><Row rowNum='14'><Column name='ADX_TAG_CODE'><Val>2791035173</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>18,630</Val></Column><Column name='ADX_COVERAGE'><Val>40.69%</Val></Column><Column name='ADX_REQUESTS'><Val>45,787</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>18,476</Val></Column><Column name='ADX_CLICKS'><Val>209</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$7.78</Val></Column></Row><Row rowNum='15'><Column name='ADX_TAG_CODE'><Val>2992275414</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>50,054</Val></Column><Column name='ADX_COVERAGE'><Val>19.73%</Val></Column><Column name='ADX_REQUESTS'><Val>253,724</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>28,560</Val></Column><Column name='ADX_CLICKS'><Val>517</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$39.10</Val></Column></Row><Row rowNum='16'><Column name='ADX_TAG_CODE'><Val>3226257864</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>46,240</Val></Column><Column name='ADX_COVERAGE'><Val>33.03%</Val></Column><Column name='ADX_REQUESTS'><Val>140,006</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>18,288</Val></Column><Column name='ADX_CLICKS'><Val>294</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$27.62</Val></Column></Row><Row rowNum='17'><Column name='ADX_TAG_CODE'><Val>3357395651</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>195,980</Val></Column><Column name='ADX_COVERAGE'><Val>9.38%</Val></Column><Column name='ADX_REQUESTS'><Val>2,089,879</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>189,457</Val></Column><Column name='ADX_CLICKS'><Val>945</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$71.23</Val></Column></Row><Row rowNum='18'><Column name='ADX_TAG_CODE'><Val>3459277400</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>43,460</Val></Column><Column name='ADX_COVERAGE'><Val>72.68%</Val></Column><Column name='ADX_REQUESTS'><Val>59,794</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>43,433</Val></Column><Column name='ADX_CLICKS'><Val>2,545</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$143.95</Val></Column></Row><Row rowNum='19'><Column name='ADX_TAG_CODE'><Val>3615638042</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>40,026</Val></Column><Column name='ADX_COVERAGE'><Val>7.13%</Val></Column><Column name='ADX_REQUESTS'><Val>561,312</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>26,721</Val></Column><Column name='ADX_CLICKS'><Val>353</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$35.76</Val></Column></Row><Row rowNum='20'><Column name='ADX_TAG_CODE'><Val>3671177560</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>2,296</Val></Column><Column name='ADX_COVERAGE'><Val>19.50%</Val></Column><Column name='ADX_REQUESTS'><Val>11,776</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>761</Val></Column><Column name='ADX_CLICKS'><Val>5</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$4.25</Val></Column></Row><Row rowNum='21'><Column name='ADX_TAG_CODE'><Val>3681031703</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>274,844</Val></Column><Column name='ADX_COVERAGE'><Val>20.97%</Val></Column><Column name='ADX_REQUESTS'><Val>1,310,667</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>105,118</Val></Column><Column name='ADX_CLICKS'><Val>771</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$44.13</Val></Column></Row><Row rowNum='22'><Column name='ADX_TAG_CODE'><Val>3971019227</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>2,037,087</Val></Column><Column name='ADX_COVERAGE'><Val>42.01%</Val></Column><Column name='ADX_REQUESTS'><Val>4,849,561</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>49,781</Val></Column><Column name='ADX_CLICKS'><Val>2,193</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$80.53</Val></Column></Row><Row rowNum='23'><Column name='ADX_TAG_CODE'><Val>4030849179</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>99,422</Val></Column><Column name='ADX_COVERAGE'><Val>10.37%</Val></Column><Column name='ADX_REQUESTS'><Val>959,127</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>98,629</Val></Column><Column name='ADX_CLICKS'><Val>857</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$122.91</Val></Column></Row><Row rowNum='24'><Column name='ADX_TAG_CODE'><Val>4061119161</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>658,425</Val></Column><Column name='ADX_COVERAGE'><Val>36.81%</Val></Column><Column name='ADX_REQUESTS'><Val>1,788,809</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>35,561</Val></Column><Column name='ADX_CLICKS'><Val>1,198</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$62.68</Val></Column></Row><Row rowNum='25'><Column name='ADX_TAG_CODE'><Val>4305357717</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>189,953</Val></Column><Column name='ADX_COVERAGE'><Val>30.78%</Val></Column><Column name='ADX_REQUESTS'><Val>617,200</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>189,616</Val></Column><Column name='ADX_CLICKS'><Val>2,039</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$128.42</Val></Column></Row><Row rowNum='26'><Column name='ADX_TAG_CODE'><Val>4378029445</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>1,248</Val></Column><Column name='ADX_COVERAGE'><Val>41.19%</Val></Column><Column name='ADX_REQUESTS'><Val>3,030</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>1,237</Val></Column><Column name='ADX_CLICKS'><Val>27</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$4.38</Val></Column></Row><Row rowNum='27'><Column name='ADX_TAG_CODE'><Val>4539340167</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>134,052</Val></Column><Column name='ADX_COVERAGE'><Val>26.98%</Val></Column><Column name='ADX_REQUESTS'><Val>496,772</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>38,362</Val></Column><Column name='ADX_CLICKS'><Val>566</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$24.25</Val></Column></Row><Row rowNum='28'><Column name='ADX_TAG_CODE'><Val>4904184441</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>86,930</Val></Column><Column name='ADX_COVERAGE'><Val>29.49%</Val></Column><Column name='ADX_REQUESTS'><Val>294,786</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>19,968</Val></Column><Column name='ADX_CLICKS'><Val>182</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$13.29</Val></Column></Row><Row rowNum='29'><Column name='ADX_TAG_CODE'><Val>5039631277</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>11,356</Val></Column><Column name='ADX_COVERAGE'><Val>14.44%</Val></Column><Column name='ADX_REQUESTS'><Val>78,618</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>11,003</Val></Column><Column name='ADX_CLICKS'><Val>48</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$7.50</Val></Column></Row><Row rowNum='30'><Column name='ADX_TAG_CODE'><Val>5233931922</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>6,718</Val></Column><Column name='ADX_COVERAGE'><Val>1.06%</Val></Column><Column name='ADX_REQUESTS'><Val>631,231</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>6,605</Val></Column><Column name='ADX_CLICKS'><Val>22</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$6.99</Val></Column></Row><Row rowNum='31'><Column name='ADX_TAG_CODE'><Val>5424394127</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>150,741</Val></Column><Column name='ADX_COVERAGE'><Val>39.17%</Val></Column><Column name='ADX_REQUESTS'><Val>384,813</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>116,852</Val></Column><Column name='ADX_CLICKS'><Val>1,290</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$115.28</Val></Column></Row><Row rowNum='32'><Column name='ADX_TAG_CODE'><Val>5852422470</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>937,473</Val></Column><Column name='ADX_COVERAGE'><Val>61.23%</Val></Column><Column name='ADX_REQUESTS'><Val>1,531,144</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>230,684</Val></Column><Column name='ADX_CLICKS'><Val>3,017</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$189.52</Val></Column></Row><Row rowNum='33'><Column name='ADX_TAG_CODE'><Val>6307196309</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>78,462</Val></Column><Column name='ADX_COVERAGE'><Val>26.93%</Val></Column><Column name='ADX_REQUESTS'><Val>291,381</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>37,117</Val></Column><Column name='ADX_CLICKS'><Val>233</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$16.00</Val></Column></Row><Row rowNum='34'><Column name='ADX_TAG_CODE'><Val>6355438104</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>659,571</Val></Column><Column name='ADX_COVERAGE'><Val>94.14%</Val></Column><Column name='ADX_REQUESTS'><Val>700,611</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>633,793</Val></Column><Column name='ADX_CLICKS'><Val>4,472</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$315.77</Val></Column></Row><Row rowNum='35'><Column name='ADX_TAG_CODE'><Val>6453085641</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>53,960</Val></Column><Column name='ADX_COVERAGE'><Val>21.53%</Val></Column><Column name='ADX_REQUESTS'><Val>250,596</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>42,268</Val></Column><Column name='ADX_CLICKS'><Val>412</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$30.50</Val></Column></Row><Row rowNum='36'><Column name='ADX_TAG_CODE'><Val>6481245432</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>16,575</Val></Column><Column name='ADX_COVERAGE'><Val>34.57%</Val></Column><Column name='ADX_REQUESTS'><Val>47,946</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>16,536</Val></Column><Column name='ADX_CLICKS'><Val>191</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$7.37</Val></Column></Row><Row rowNum='37'><Column name='ADX_TAG_CODE'><Val>6560115709</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>127,279</Val></Column><Column name='ADX_COVERAGE'><Val>15.53%</Val></Column><Column name='ADX_REQUESTS'><Val>819,380</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>115,730</Val></Column><Column name='ADX_CLICKS'><Val>809</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$124.97</Val></Column></Row><Row rowNum='38'><Column name='ADX_TAG_CODE'><Val>6601726229</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>24,611</Val></Column><Column name='ADX_COVERAGE'><Val>8.55%</Val></Column><Column name='ADX_REQUESTS'><Val>287,812</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>18,527</Val></Column><Column name='ADX_CLICKS'><Val>278</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$14.09</Val></Column></Row><Row rowNum='39'><Column name='ADX_TAG_CODE'><Val>6621041809</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>9,878</Val></Column><Column name='ADX_COVERAGE'><Val>46.31%</Val></Column><Column name='ADX_REQUESTS'><Val>21,328</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>1,826</Val></Column><Column name='ADX_CLICKS'><Val>37</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$5.91</Val></Column></Row><Row rowNum='40'><Column name='ADX_TAG_CODE'><Val>6987632285</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>103,068</Val></Column><Column name='ADX_COVERAGE'><Val>61.05%</Val></Column><Column name='ADX_REQUESTS'><Val>168,828</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>102,744</Val></Column><Column name='ADX_CLICKS'><Val>1,682</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$45.96</Val></Column></Row><Row rowNum='41'><Column name='ADX_TAG_CODE'><Val>7107013200</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>211,275</Val></Column><Column name='ADX_COVERAGE'><Val>19.29%</Val></Column><Column name='ADX_REQUESTS'><Val>1,094,975</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>102,449</Val></Column><Column name='ADX_CLICKS'><Val>900</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$62.27</Val></Column></Row><Row rowNum='42'><Column name='ADX_TAG_CODE'><Val>7165504773</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>118,886</Val></Column><Column name='ADX_COVERAGE'><Val>27.47%</Val></Column><Column name='ADX_REQUESTS'><Val>432,846</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>63,731</Val></Column><Column name='ADX_CLICKS'><Val>1,167</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$42.04</Val></Column></Row><Row rowNum='43'><Column name='ADX_TAG_CODE'><Val>7206948188</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>131,247</Val></Column><Column name='ADX_COVERAGE'><Val>22.22%</Val></Column><Column name='ADX_REQUESTS'><Val>590,648</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>100,833</Val></Column><Column name='ADX_CLICKS'><Val>2,685</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$696.62</Val></Column></Row><Row rowNum='44'><Column name='ADX_TAG_CODE'><Val>7579515679</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>11,219</Val></Column><Column name='ADX_COVERAGE'><Val>35.96%</Val></Column><Column name='ADX_REQUESTS'><Val>31,200</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>3,258</Val></Column><Column name='ADX_CLICKS'><Val>69</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$9.55</Val></Column></Row><Row rowNum='45'><Column name='ADX_TAG_CODE'><Val>7888044518</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>29,432</Val></Column><Column name='ADX_COVERAGE'><Val>12.49%</Val></Column><Column name='ADX_REQUESTS'><Val>235,725</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>22,383</Val></Column><Column name='ADX_CLICKS'><Val>291</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$15.96</Val></Column></Row><Row rowNum='46'><Column name='ADX_TAG_CODE'><Val>7981415653</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>65,887</Val></Column><Column name='ADX_COVERAGE'><Val>18.49%</Val></Column><Column name='ADX_REQUESTS'><Val>356,269</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>23,507</Val></Column><Column name='ADX_CLICKS'><Val>251</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$11.00</Val></Column></Row><Row rowNum='47'><Column name='ADX_TAG_CODE'><Val>8231366035</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>16,657</Val></Column><Column name='ADX_COVERAGE'><Val>4.19%</Val></Column><Column name='ADX_REQUESTS'><Val>397,320</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>16,491</Val></Column><Column name='ADX_CLICKS'><Val>90</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$15.28</Val></Column></Row><Row rowNum='48'><Column name='ADX_TAG_CODE'><Val>8338337331</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>822,825</Val></Column><Column name='ADX_COVERAGE'><Val>41.55%</Val></Column><Column name='ADX_REQUESTS'><Val>1,980,244</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>466,120</Val></Column><Column name='ADX_CLICKS'><Val>2,684</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$116.70</Val></Column></Row><Row rowNum='49'><Column name='ADX_TAG_CODE'><Val>8348680420</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>64,203</Val></Column><Column name='ADX_COVERAGE'><Val>37.37%</Val></Column><Column name='ADX_REQUESTS'><Val>171,821</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>64,084</Val></Column><Column name='ADX_CLICKS'><Val>1,036</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$25.70</Val></Column></Row><Row rowNum='50'><Column name='ADX_TAG_CODE'><Val>8731930651</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>2,071</Val></Column><Column name='ADX_COVERAGE'><Val>25.85%</Val></Column><Column name='ADX_REQUESTS'><Val>8,011</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>467</Val></Column><Column name='ADX_CLICKS'><Val>10</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$2.54</Val></Column></Row><Row rowNum='51'><Column name='ADX_TAG_CODE'><Val>9486074077</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>237,000</Val></Column><Column name='ADX_COVERAGE'><Val>54.08%</Val></Column><Column name='ADX_REQUESTS'><Val>438,252</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>70,267</Val></Column><Column name='ADX_CLICKS'><Val>511</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$39.75</Val></Column></Row><Row rowNum='52'><Column name='ADX_TAG_CODE'><Val>9661762723</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>53,935</Val></Column><Column name='ADX_COVERAGE'><Val>15.37%</Val></Column><Column name='ADX_REQUESTS'><Val>350,820</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>53,437</Val></Column><Column name='ADX_CLICKS'><Val>174</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$16.07</Val></Column></Row><Row rowNum='53'><Column name='ADX_TAG_CODE'><Val>9989473436</Val></Column><Column name='ADX_MATCHED_QUERIES'><Val>31,067</Val></Column><Column name='ADX_COVERAGE'><Val>9.80%</Val></Column><Column name='ADX_REQUESTS'><Val>316,994</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>15,070</Val></Column><Column name='ADX_CLICKS'><Val>196</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$19.59</Val></Column></Row><Total rowNum='54'><Column name='ADX_TAG_CODE'><Val/></Column><Column name='ADX_MATCHED_QUERIES'><Val>11,394,663</Val></Column><Column name='ADX_COVERAGE'><Val>29.10%</Val></Column><Column name='ADX_REQUESTS'><Val>39,161,009</Val></Column><Column name='ADX_AD_IMPRESSIONS_ADJUSTED'><Val>4,037,096</Val></Column><Column name='ADX_CLICKS'><Val>46,327</Val></Column><Column name='ADX_ESTIMATED_REVENUE'><Val>$3,786.02</Val></Column></Total></DataSet></ReportData></Report>";
		XMLObject xmlObj = XMLParser.parse(content);
		xmlObj.getAllChildTags("Row").forEach(row -> {
			StringBuilder sb = new StringBuilder();
			row.getChildTags("Column").forEach(item -> {
				sb.append(item.getAttr("name")).append(" ");
			});
			System.out.println(sb);
		});
	}
}