package v800_trainer;

/*
 * Types.java
 *
 * SourceFile is part of Chainwheel

 * Variables and Map Definition for decoding v800 file
 */



/**
 *
 * @author and migration to java volker hochholzer
 * 
 * Chainwheel and all dependend source files is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Chainwheel is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

 */
/*
    Copyright 2014-2015 Paul Colby

    This file is part of Bipolar.

    Bipolar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Biplar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Bipolar.  If not, see <http://www.gnu.org/licenses/>.
*/



import java.util.HashMap;


public class Types{

public static String[][] copy(String[][]Source){
    String[][] temp = new String[Source.length][Source[0].length];
     for (int i = 0; i< Source.length;i++)
        
            temp[i] = Source[i].clone();
     return temp;
            
}
    
    public static Object[][] CreateExercise =
     {{"1","start",ScalarType.EmbeddedMessage}
        ,{"1/1","date",ScalarType.EmbeddedMessage}
        ,{"1/1/1","year",ScalarType.Uint32}
        ,{"1/1/2","month",ScalarType.Uint32}
        ,{"1/1/3","day",ScalarType.Uint32}
        ,{"1/2","time",ScalarType.EmbeddedMessage}
        ,{"1/2/1","hour",ScalarType.Uint32}
        ,{"1/2/2","minute",ScalarType.Uint32}
        ,{"1/2/3","seconds",ScalarType.Uint32}
        ,{"1/2/4","milliseconds",ScalarType.Uint32}
        ,{"1/4","offset",ScalarType.Int32}
        ,{"2","duration",ScalarType.EmbeddedMessage}
        ,{"2/1","hours",ScalarType.Uint32}
        ,{"2/2","minutes",ScalarType.Uint32}
        ,{"2/3","seconds",ScalarType.Uint32}
        ,{"2/4","milliseconds",ScalarType.Uint32}
        ,{"3","sport",ScalarType.EmbeddedMessage}
        ,{"3/1","value",ScalarType.Uint64}
        ,{"4","distance",ScalarType.Float}
        ,{"5","calories",ScalarType.Uint32}
        ,{"6","training-load",ScalarType.EmbeddedMessage}
        ,{"6/1","load-value",ScalarType.Uint32}
        ,{"6/2","recovery-time",ScalarType.EmbeddedMessage}
        ,{"6/2/1","hours",ScalarType.Uint32}
        ,{"6/2/2","minutes",ScalarType.Uint32}
        ,{"6/2/3","seconds",ScalarType.Uint32}
        ,{"6/2/4","milliseconds",ScalarType.Uint32}
        ,{"6/3","carbs",ScalarType.Uint32}
        ,{"6/4","protein",ScalarType.Uint32}
        ,{"6/5","fat",ScalarType.Uint32}
        ,{"7","sensors",ScalarType.Enumerator}
        ,{"9","running-index",ScalarType.EmbeddedMessage}
        ,{"9/1","value",ScalarType.Uint32}
        ,{"9/2","duration",ScalarType.EmbeddedMessage}
        ,{"9/2/1","hours",ScalarType.Uint32}
        ,{"9/2/2","minutes",ScalarType.Uint32}
        ,{"9/2/3","seconds",ScalarType.Uint32}
        ,{"9/2/4","milliseconds",ScalarType.Uint32}
        ,{"10","ascent",ScalarType.Float}
        ,{"11","descent",ScalarType.Float}
        ,{"12","latitude",ScalarType.Double}
        ,{"13","longitude",ScalarType.Double}
        ,{"14","place",ScalarType.String}
        ,{"15","target-result",ScalarType.EmbeddedMessage}
        ,{"15/1","index",ScalarType.Uint32}
        ,{"15/2","reached",ScalarType.Bool}
        ,{"15/3","end-time",ScalarType.EmbeddedMessage}
        ,{"15/3/1","hours",ScalarType.Uint32}
        ,{"15/3/2","minutes",ScalarType.Uint32}
        ,{"15/3/3","seconds",ScalarType.Uint32}
        ,{"15/3/4","milliseconds",ScalarType.Uint32}
        ,{"15/4","race-pace-result",ScalarType.EmbeddedMessage}
        ,{"15/4/1","completed",ScalarType.EmbeddedMessage}
        ,{"15/4/1/1","hours",ScalarType.Uint32}
        ,{"15/4/1/2","minutes",ScalarType.Uint32}
        ,{"15/4/1/3","seconds",ScalarType.Uint32}
        ,{"15/4/1/4","milliseconds",ScalarType.Uint32}
        ,{"15/4/2","heartrate",ScalarType.Uint32}
        ,{"15/4/3","speed",ScalarType.Float}
        ,{"15/5","volume-target",ScalarType.EmbeddedMessage}
        ,{"15/5/1","target-type",ScalarType.Enumerator}
        ,{"15/5/2","duration",ScalarType.EmbeddedMessage}
        ,{"15/5/2/1","hours",ScalarType.Uint32}
        ,{"15/5/2/2","minutes",ScalarType.Uint32}
        ,{"15/5/2/3","seconds",ScalarType.Uint32}
        ,{"15/5/2/4","milliseconds",ScalarType.Uint32}
        ,{"15/5/3","distance",ScalarType.Float}
        ,{"15/5/4","calores",ScalarType.Uint32}
        ,{"16","exercise-counters",ScalarType.EmbeddedMessage}
        ,{"16/1","sprint-count",ScalarType.Uint32}
        ,{"17","speed-calibration-offset",ScalarType.Float}

            };

  public static Object[][] CreateSession=
 {{"1","start",ScalarType.EmbeddedMessage}
        ,{"1/1","date",ScalarType.EmbeddedMessage}
        ,{"1/1/1","year",ScalarType.Uint32}
        ,{"1/1/2","month",ScalarType.Uint32}
        ,{"1/1/3","day",ScalarType.Uint32}
        ,{"1/2","time",ScalarType.EmbeddedMessage}
        ,{"1/2/1","hour",ScalarType.Uint32}
        ,{"1/2/2","minute",ScalarType.Uint32}
        ,{"1/2/3","seconds",ScalarType.Uint32}
        ,{"1/2/4","milliseconds",ScalarType.Uint32}
        ,{"1/4","offset",ScalarType.Int32}
        ,{"2","exercise-count",ScalarType.Uint32}
        ,{"3","device",ScalarType.String}
        ,{"4","model",ScalarType.String}
        ,{"5","duration",ScalarType.EmbeddedMessage}
        ,{"5/1","hours",ScalarType.Uint32}
        ,{"5/2","minutes",ScalarType.Uint32}
        ,{"5/3","seconds",ScalarType.Uint32}
        ,{"5/4","milliseconds",ScalarType.Uint32}
        ,{"6","distance",ScalarType.Float}
        ,{"7","calories",ScalarType.Uint32}
        ,{"8","heartreat",ScalarType.EmbeddedMessage}
        ,{"8/1","average",ScalarType.Uint32}
        ,{"8/2","maximum",ScalarType.Uint32}
        ,{"9","heartrate-duration",ScalarType.EmbeddedMessage}
        ,{"9/1","hours",ScalarType.Uint32}
        ,{"9/2","minutes",ScalarType.Uint32}
        ,{"9/3","seconds",ScalarType.Uint32}
        ,{"9/4","milliseconds",ScalarType.Uint32}
        ,{"10","training-load",ScalarType.EmbeddedMessage}
        ,{"10/1","load-value",ScalarType.Uint32}
        ,{"10/2","recovery-time",ScalarType.EmbeddedMessage}
        ,{"10/2/1","hours",ScalarType.Uint32}
        ,{"10/2/2","minutes",ScalarType.Uint32}
        ,{"10/2/3","seconds",ScalarType.Uint32}
        ,{"10/2/4","milliseconds",ScalarType.Uint32}
        ,{"10/3","carbs",ScalarType.Uint32}
        ,{"10/4","protein",ScalarType.Uint32}
        ,{"10/5","fat",ScalarType.Uint32}
        ,{"11","session-name",ScalarType.EmbeddedMessage}
        ,{"11/1","text",ScalarType.String}
        ,{"12","feeling",ScalarType.Float}
        ,{"13","note",ScalarType.EmbeddedMessage}
        ,{"13/1","text",ScalarType.String}
        ,{"14","place",ScalarType.EmbeddedMessage}
        ,{"14/1","text",ScalarType.String}
        ,{"15","latitude",ScalarType.Double}
        ,{"16","longitude",ScalarType.Double}
        ,{"17","benefit",ScalarType.Enumerator}
        ,{"18","sport",ScalarType.EmbeddedMessage}
        ,{"18/1","value",ScalarType.Uint64}
        ,{"19","training-target",ScalarType.EmbeddedMessage}
        ,{"19/1","value",ScalarType.Uint64}
        ,{"19/2","last-modified",ScalarType.EmbeddedMessage}
        ,{"19/2/1","date",ScalarType.EmbeddedMessage}
        ,{"19/2/1/1","year",ScalarType.Uint32}
        ,{"19/2/1/2","month",ScalarType.Uint32}
        ,{"19/2/1/3","day",ScalarType.Uint32}
        ,{"19/2/2","time",ScalarType.EmbeddedMessage}
        ,{"19/2/2/1","hour",ScalarType.Uint32}
        ,{"19/2/2/2","minute",ScalarType.Uint32}
        ,{"19/2/2/3","seconds",ScalarType.Uint32}
        ,{"19/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"20","end",ScalarType.EmbeddedMessage}
        ,{"20/1","date",ScalarType.EmbeddedMessage}
        ,{"20/1/1","year",ScalarType.Uint32}
        ,{"20/1/2","month",ScalarType.Uint32}
        ,{"20/1/3","day",ScalarType.Uint32}
        ,{"20/2","time",ScalarType.EmbeddedMessage}
        ,{"20/2/1","hour",ScalarType.Uint32}
        ,{"20/2/2","minute",ScalarType.Uint32}
        ,{"20/2/3","seconds",ScalarType.Uint32}
        ,{"20/2/4","milliseconds",ScalarType.Uint32}
        ,{"20/4","offset",ScalarType.Int32}

            };
  public static Object[][] Laps =
 {{"1","laps",ScalarType.EmbeddedMessage}
        ,{"1/1","header",ScalarType.EmbeddedMessage}
        ,{"1/1/1","split-time",ScalarType.EmbeddedMessage}
        ,{"1/1/1/1","hours",ScalarType.Uint32}
        ,{"1/1/1/2","minutes",ScalarType.Uint32}
        ,{"1/1/1/3","seconds",ScalarType.Uint32}
        ,{"1/1/1/4","milliseconds",ScalarType.Uint32}
        ,{"1/1/2","duration",ScalarType.EmbeddedMessage}
        ,{"1/1/2/1","hours",ScalarType.Uint32}
        ,{"1/1/2/2","minutes",ScalarType.Uint32}
        ,{"1/1/2/3","seconds",ScalarType.Uint32}
        ,{"1/1/2/4","milliseconds",ScalarType.Uint32}
        ,{"1/1/3","distance",ScalarType.Float}
        ,{"1/1/4","ascent",ScalarType.Float}
        ,{"1/1/5","descent",ScalarType.Float}
        ,{"1/1/6","lap-type",ScalarType.Enumerator}
        ,{"1/2","stats",ScalarType.EmbeddedMessage}
        ,{"1/2/1","heartrate",ScalarType.EmbeddedMessage}
        ,{"1/2/1/1","average",ScalarType.Uint32}
        ,{"1/2/1/2","maximum",ScalarType.Uint32}
        ,{"1/2/1/3","minimum",ScalarType.Uint32}
        ,{"1/2/2","speed",ScalarType.EmbeddedMessage}
        ,{"1/2/2/1","average",ScalarType.Float}
        ,{"1/2/2/2","maximum",ScalarType.Float}
        ,{"1/2/3","cadence",ScalarType.EmbeddedMessage}
        ,{"1/2/3/1","average",ScalarType.Uint32}
        ,{"1/2/3/2","maximum",ScalarType.Uint32}
        ,{"1/2/4","power",ScalarType.EmbeddedMessage}
        ,{"1/2/4/1","average",ScalarType.Uint32}
        ,{"1/2/4/2","maximum",ScalarType.Uint32}
        ,{"1/2/5","pedaling",ScalarType.EmbeddedMessage}
        ,{"1/2/5/1","average",ScalarType.Uint32}
        ,{"1/2/6","incline",ScalarType.EmbeddedMessage}
        ,{"1/2/6/1","average",ScalarType.Float}
        ,{"1/2/7","stride",ScalarType.EmbeddedMessage}
        ,{"1/2/7/1","average",ScalarType.Uint32}
        ,{"2","summary",ScalarType.EmbeddedMessage}
        ,{"2/1","best-duration",ScalarType.EmbeddedMessage}
        ,{"2/1/1","hours",ScalarType.Uint32}
        ,{"2/1/2","minutes",ScalarType.Uint32}
        ,{"2/1/3","seconds",ScalarType.Uint32}
        ,{"2/1/4","milliseconds",ScalarType.Uint32}
        ,{"2/2","average-duration",ScalarType.EmbeddedMessage}
        ,{"2/2/1","hours",ScalarType.Uint32}
        ,{"2/2/2","minutes",ScalarType.Uint32}
        ,{"2/2/3","seconds",ScalarType.Uint32}
        ,{"2/2/4","milliseconds",ScalarType.Uint32}

            };
  public static Object[][] PhysicalInformation =
 {{"1","birthday",ScalarType.EmbeddedMessage}
        ,{"1/1","value",ScalarType.EmbeddedMessage}
        ,{"1/1/1","year",ScalarType.Uint32}
        ,{"1/1/2","month",ScalarType.Uint32}
        ,{"1/1/3","day",ScalarType.Uint32}
        ,{"1/2","modified",ScalarType.EmbeddedMessage}
        ,{"1/2/1","date",ScalarType.EmbeddedMessage}
        ,{"1/2/1/1","year",ScalarType.Uint32}
        ,{"1/2/1/2","month",ScalarType.Uint32}
        ,{"1/2/1/3","day",ScalarType.Uint32}
        ,{"1/2/2","time",ScalarType.EmbeddedMessage}
        ,{"1/2/2/1","hour",ScalarType.Uint32}
        ,{"1/2/2/2","minute",ScalarType.Uint32}
        ,{"1/2/2/3","seconds",ScalarType.Uint32}
        ,{"1/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"2","gender",ScalarType.EmbeddedMessage}
        ,{"2/1","value",ScalarType.Enumerator}
        ,{"2/2","modified",ScalarType.EmbeddedMessage}
        ,{"2/2/1","date",ScalarType.EmbeddedMessage}
        ,{"2/2/1/1","year",ScalarType.Uint32}
        ,{"2/2/1/2","month",ScalarType.Uint32}
        ,{"2/2/1/3","day",ScalarType.Uint32}
        ,{"2/2/2","time",ScalarType.EmbeddedMessage}
        ,{"2/2/2/1","hour",ScalarType.Uint32}
        ,{"2/2/2/2","minute",ScalarType.Uint32}
        ,{"2/2/2/3","seconds",ScalarType.Uint32}
        ,{"2/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"3","weight",ScalarType.EmbeddedMessage}
        ,{"3/1","value",ScalarType.Float}
        ,{"3/2","modified",ScalarType.EmbeddedMessage}
        ,{"3/2/1","date",ScalarType.EmbeddedMessage}
        ,{"3/2/1/1","year",ScalarType.Uint32}
        ,{"3/2/1/2","month",ScalarType.Uint32}
        ,{"3/2/1/3","day",ScalarType.Uint32}
        ,{"3/2/2","time",ScalarType.EmbeddedMessage}
        ,{"3/2/2/1","hour",ScalarType.Uint32}
        ,{"3/2/2/2","minute",ScalarType.Uint32}
        ,{"3/2/2/3","seconds",ScalarType.Uint32}
        ,{"3/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"4","height",ScalarType.EmbeddedMessage}
        ,{"4/1","value",ScalarType.Float}
        ,{"4/2","modified",ScalarType.EmbeddedMessage}
        ,{"4/2/1","date",ScalarType.EmbeddedMessage}
        ,{"4/2/1/1","year",ScalarType.Uint32}
        ,{"4/2/1/2","month",ScalarType.Uint32}
        ,{"4/2/1/3","day",ScalarType.Uint32}
        ,{"4/2/2","time",ScalarType.EmbeddedMessage}
        ,{"4/2/2/1","hour",ScalarType.Uint32}
        ,{"4/2/2/2","minute",ScalarType.Uint32}
        ,{"4/2/2/3","seconds",ScalarType.Uint32}
        ,{"4/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"5","maximum-heartrate",ScalarType.EmbeddedMessage}
        ,{"5/1","value",ScalarType.Uint32}
        ,{"5/2","modified",ScalarType.EmbeddedMessage}
        ,{"5/2/1","date",ScalarType.EmbeddedMessage}
        ,{"5/2/1/1","year",ScalarType.Uint32}
        ,{"5/2/1/2","month",ScalarType.Uint32}
        ,{"5/2/1/3","day",ScalarType.Uint32}
        ,{"5/2/2","time",ScalarType.EmbeddedMessage}
        ,{"5/2/2/1","hour",ScalarType.Uint32}
        ,{"5/2/2/2","minute",ScalarType.Uint32}
        ,{"5/2/2/3","seconds",ScalarType.Uint32}
        ,{"5/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"5/3","source",ScalarType.Enumerator}
        ,{"6","resting-heartrate",ScalarType.EmbeddedMessage}
        ,{"6/1","value",ScalarType.Uint32}
        ,{"6/2","modified",ScalarType.EmbeddedMessage}
        ,{"6/2/1","date",ScalarType.EmbeddedMessage}
        ,{"6/2/1/1","year",ScalarType.Uint32}
        ,{"6/2/1/2","month",ScalarType.Uint32}
        ,{"6/2/1/3","day",ScalarType.Uint32}
        ,{"6/2/2","time",ScalarType.EmbeddedMessage}
        ,{"6/2/2/1","hour",ScalarType.Uint32}
        ,{"6/2/2/2","minute",ScalarType.Uint32}
        ,{"6/2/2/3","seconds",ScalarType.Uint32}
        ,{"6/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"6/3","source",ScalarType.Enumerator}
        ,{"8","aerobic-threshold",ScalarType.EmbeddedMessage}
        ,{"8/1","value",ScalarType.Uint32}
        ,{"8/2","modified",ScalarType.EmbeddedMessage}
        ,{"8/2/1","date",ScalarType.EmbeddedMessage}
        ,{"8/2/1/1","year",ScalarType.Uint32}
        ,{"8/2/1/2","month",ScalarType.Uint32}
        ,{"8/2/1/3","day",ScalarType.Uint32}
        ,{"8/2/2","time",ScalarType.EmbeddedMessage}
        ,{"8/2/2/1","hour",ScalarType.Uint32}
        ,{"8/2/2/2","minute",ScalarType.Uint32}
        ,{"8/2/2/3","seconds",ScalarType.Uint32}
        ,{"8/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"8/3","source",ScalarType.Enumerator}
        ,{"9","anaerobic-threshold",ScalarType.EmbeddedMessage}
        ,{"9/1","value",ScalarType.Uint32}
        ,{"9/2","modified",ScalarType.EmbeddedMessage}
        ,{"9/2/1","date",ScalarType.EmbeddedMessage}
        ,{"9/2/1/1","year",ScalarType.Uint32}
        ,{"9/2/1/2","month",ScalarType.Uint32}
        ,{"9/2/1/3","day",ScalarType.Uint32}
        ,{"9/2/2","time",ScalarType.EmbeddedMessage}
        ,{"9/2/2/1","hour",ScalarType.Uint32}
        ,{"9/2/2/2","minute",ScalarType.Uint32}
        ,{"9/2/2/3","seconds",ScalarType.Uint32}
        ,{"9/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"9/3","source",ScalarType.Enumerator}
        ,{"10","vo2max",ScalarType.EmbeddedMessage}
        ,{"10/1","value",ScalarType.Uint32}
        ,{"10/2","modified",ScalarType.EmbeddedMessage}
        ,{"10/2/1","date",ScalarType.EmbeddedMessage}
        ,{"10/2/1/1","year",ScalarType.Uint32}
        ,{"10/2/1/2","month",ScalarType.Uint32}
        ,{"10/2/1/3","day",ScalarType.Uint32}
        ,{"10/2/2","time",ScalarType.EmbeddedMessage}
        ,{"10/2/2/1","hour",ScalarType.Uint32}
        ,{"10/2/2/2","minute",ScalarType.Uint32}
        ,{"10/2/2/3","seconds",ScalarType.Uint32}
        ,{"10/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"10/3","source",ScalarType.Enumerator}
        ,{"11","training-background",ScalarType.EmbeddedMessage}
        ,{"11/1","value",ScalarType.Enumerator}
        ,{"11/2","modified",ScalarType.EmbeddedMessage}
        ,{"11/2/1","date",ScalarType.EmbeddedMessage}
        ,{"11/2/1/1","year",ScalarType.Uint32}
        ,{"11/2/1/2","month",ScalarType.Uint32}
        ,{"11/2/1/3","day",ScalarType.Uint32}
        ,{"11/2/2","time",ScalarType.EmbeddedMessage}
        ,{"11/2/2/1","hour",ScalarType.Uint32}
        ,{"11/2/2/2","minute",ScalarType.Uint32}
        ,{"11/2/2/3","seconds",ScalarType.Uint32}
        ,{"11/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"13","13",ScalarType.EmbeddedMessage}
        ,{"13/1","13/1",ScalarType.Float}
        ,{"13/2","modified",ScalarType.EmbeddedMessage}
        ,{"13/2/1","date",ScalarType.EmbeddedMessage}
        ,{"13/2/1/1","year",ScalarType.Uint32}
        ,{"13/2/1/2","month",ScalarType.Uint32}
        ,{"13/2/1/3","day",ScalarType.Uint32}
        ,{"13/2/2","time",ScalarType.EmbeddedMessage}
        ,{"13/2/2/1","hour",ScalarType.Uint32}
        ,{"13/2/2/2","minute",ScalarType.Uint32}
        ,{"13/2/2/3","seconds",ScalarType.Uint32}
        ,{"13/2/2/4","milliseconds",ScalarType.Uint32}
        ,{"100","modified",ScalarType.EmbeddedMessage}
        ,{"100/1","date",ScalarType.EmbeddedMessage}
        ,{"100/1/1","year",ScalarType.Uint32}
        ,{"100/1/2","month",ScalarType.Uint32}
        ,{"100/1/3","day",ScalarType.Uint32}
        ,{"100/2","time",ScalarType.EmbeddedMessage}
        ,{"100/2/1","hour",ScalarType.Uint32}
        ,{"100/2/2","minute",ScalarType.Uint32}
        ,{"100/2/3","seconds",ScalarType.Uint32}
        ,{"100/2/4","milliseconds",ScalarType.Uint32}

            };

  public static Object[][]Route =
 {{"1","duration",ScalarType.Uint32}
        ,{"2","latitude",ScalarType.Double}
        ,{"3","longitude",ScalarType.Double}
        ,{"4","altitude",ScalarType.Sint32}
        ,{"5","satellites",ScalarType.Uint32}
        ,{"9","timestamp",ScalarType.EmbeddedMessage}
        ,{"9/1","date",ScalarType.EmbeddedMessage}
        ,{"9/1/1","year",ScalarType.Uint32}
        ,{"9/1/2","month",ScalarType.Uint32}
        ,{"9/1/3","day",ScalarType.Uint32}
        ,{"9/2","time",ScalarType.EmbeddedMessage}
        ,{"9/2/1","hour",ScalarType.Uint32}
        ,{"9/2/2","minute",ScalarType.Uint32}
        ,{"9/2/3","seconds",ScalarType.Uint32}
        ,{"9/2/4","milliseconds",ScalarType.Uint32}

            };

  
  public static Object[][]Samples=
{{"1","record-interval",ScalarType.EmbeddedMessage}
        ,{"1/1","hours",ScalarType.Uint32}
        ,{"1/2","minutes",ScalarType.Uint32}
        ,{"1/3","seconds",ScalarType.Uint32}
        ,{"1/4","milliseconds",ScalarType.Uint32}
        ,{"2","heartrate",ScalarType.Uint32}
        ,{"3","heartrate-offline",ScalarType.EmbeddedMessage}
        ,{"3/1","start-index",ScalarType.Uint32}
        ,{"3/2","stop-index",ScalarType.Uint32}
        ,{"4","cadence",ScalarType.Uint32}
        ,{"5","cadence-offline",ScalarType.EmbeddedMessage}
        ,{"5/1","start-index",ScalarType.Uint32}
        ,{"5/2","stop-index",ScalarType.Uint32}
        ,{"6","altitude",ScalarType.Float}
        ,{"7","altitude-calibration",ScalarType.EmbeddedMessage}
        ,{"7/1","start-index",ScalarType.Uint32}
        ,{"7/2","value",ScalarType.Float}
        ,{"7/3","operation",ScalarType.Enumerator}
        ,{"7/4","cause",ScalarType.Enumerator}
        ,{"8","temperature",ScalarType.Float}
        ,{"9","speed",ScalarType.Float}
        ,{"10","speed-offline",ScalarType.EmbeddedMessage}
        ,{"10/1","start-index",ScalarType.Uint32}
        ,{"10/2","stop-index",ScalarType.Uint32}
        ,{"11","distance",ScalarType.Float}
        ,{"12","distance-offline",ScalarType.EmbeddedMessage}
        ,{"12/1","start-index",ScalarType.Uint32}
        ,{"12/2","stop-index",ScalarType.Uint32}
        ,{"13","stride-length",ScalarType.Uint32}
        ,{"14","stride-offline",ScalarType.EmbeddedMessage}
        ,{"14/1","start-index",ScalarType.Uint32}
        ,{"14/2","stop-index",ScalarType.Uint32}
        ,{"15","stride-calibration",ScalarType.EmbeddedMessage}
        ,{"15/1","start-index",ScalarType.Uint32}
        ,{"15/2","value",ScalarType.Float}
        ,{"15/3","operation",ScalarType.Enumerator}
        ,{"15/4","cause",ScalarType.Enumerator}
        ,{"16","fwd-acceleration",ScalarType.Float}
        ,{"17","moving-type",ScalarType.Enumerator}
        ,{"18","altitude-offline",ScalarType.EmbeddedMessage}
        ,{"18/1","start-index",ScalarType.Uint32}
        ,{"18/2","stop-index",ScalarType.Uint32}
        ,{"19","temperature-offline",ScalarType.EmbeddedMessage}
        ,{"19/1","start-index",ScalarType.Uint32}
        ,{"19/2","stop-index",ScalarType.Uint32}
        ,{"20","fwd-acceleration-offline",ScalarType.EmbeddedMessage}
        ,{"20/1","start-index",ScalarType.Uint32}
        ,{"20/2","stop-index",ScalarType.Uint32}
        ,{"21","moving-type-offline",ScalarType.EmbeddedMessage}
        ,{"21/1","start-index",ScalarType.Uint32}
        ,{"21/2","stop-index",ScalarType.Uint32}
        ,{"22","left-pedal-power",ScalarType.EmbeddedMessage}
        ,{"22/1","current-power",ScalarType.Int32}
        ,{"22/2","cumulative-revolutions",ScalarType.Uint32}
        ,{"22/3","cumulative-timestamp",ScalarType.Uint32}
        ,{"22/4","min-force",ScalarType.Sint32}
        ,{"22/5","max-force",ScalarType.Uint32}
        ,{"22/6","min-force-angle",ScalarType.Uint32}
        ,{"22/7","max-force-angle",ScalarType.Uint32}
        ,{"22/8","bottom-dead-spot",ScalarType.Uint32}
        ,{"22/9","top-dead-spot",ScalarType.Uint32}
        ,{"23","left-pedal-power-offline",ScalarType.EmbeddedMessage}
        ,{"23/1","start-index",ScalarType.Uint32}
        ,{"23/2","stop-index",ScalarType.Uint32}
        ,{"24","right-pedal-power",ScalarType.EmbeddedMessage}
        ,{"24/1","current-power",ScalarType.Int32}
        ,{"24/2","cumulative-revolutions",ScalarType.Uint32}
        ,{"24/3","cumulative-timestamp",ScalarType.Uint32}
        ,{"24/4","min-force",ScalarType.Sint32}
        ,{"24/5","max-force",ScalarType.Uint32}
        ,{"24/6","min-force-angle",ScalarType.Uint32}
        ,{"24/7","max-force-angle",ScalarType.Uint32}
        ,{"24/8","bottom-dead-spot",ScalarType.Uint32}
        ,{"24/9","top-dead-spot",ScalarType.Uint32}
        ,{"25","right-pedal-power-offline",ScalarType.EmbeddedMessage}
        ,{"25/1","start-index",ScalarType.Uint32}
        ,{"25/2","stop-index",ScalarType.Uint32}
        ,{"26","left-power-calibration",ScalarType.EmbeddedMessage}
        ,{"26/1","start-index",ScalarType.Uint32}
        ,{"26/2","value",ScalarType.Float}
        ,{"26/3","operation",ScalarType.Enumerator}
        ,{"26/4","cause",ScalarType.Enumerator}
        ,{"27","right-power-calibration",ScalarType.EmbeddedMessage}
        ,{"27/1","start-index",ScalarType.Uint32}
        ,{"27/2","value",ScalarType.Float}
        ,{"27/3","operation",ScalarType.Enumerator}
        ,{"27/4","cause",ScalarType.Enumerator}
        ,{"28","28 unknown",ScalarType.EmbeddedMessage}
        ,{"28/1","28/1 unknown",ScalarType.Uint32}
         ,{"28/2","28/2 unknown",ScalarType.Uint32}
                    };


         public static Object[][]Statistics=
        {{"1","heartrate",ScalarType.EmbeddedMessage}
        ,{"1/1","minimum",ScalarType.Uint32}
        ,{"1/2","average",ScalarType.Uint32}
        ,{"1/3","maximum",ScalarType.Uint32}
        ,{"2","speed",ScalarType.EmbeddedMessage}
        ,{"2/1","average",ScalarType.Float}
        ,{"2/2","maximum",ScalarType.Float}
        ,{"3","cadence",ScalarType.EmbeddedMessage}
        ,{"3/1","average",ScalarType.Uint32}
        ,{"3/2","maximum",ScalarType.Uint32}
        ,{"4","altitude",ScalarType.EmbeddedMessage}
        ,{"4/1","minimum",ScalarType.Float}
        ,{"4/2","average",ScalarType.Float}
        ,{"4/3","maximum",ScalarType.Float}
        ,{"5","power",ScalarType.EmbeddedMessage}
        ,{"5/1","average",ScalarType.Uint32}
        ,{"5/2","maximum",ScalarType.Uint32}
        ,{"6","lr_balance",ScalarType.EmbeddedMessage}
        ,{"6/1","average",ScalarType.Float}
        ,{"7","temperature",ScalarType.EmbeddedMessage}
        ,{"7/1","minimum",ScalarType.Float}
        ,{"7/2","average",ScalarType.Float}
        ,{"7/3","maximum",ScalarType.Float}
        ,{"8","activity",ScalarType.EmbeddedMessage}
        ,{"8/1","average",ScalarType.Float}
        ,{"9","stride",ScalarType.EmbeddedMessage}
        ,{"9/1","average",ScalarType.Uint32}
        ,{"9/2","maximum",ScalarType.Uint32}
        ,{"10","include",ScalarType.EmbeddedMessage}
        ,{"10/1","average",ScalarType.Float}
        ,{"10/2","maximum",ScalarType.Float}
        ,{"11","declince",ScalarType.EmbeddedMessage}
        ,{"11/1","average",ScalarType.Float}
        ,{"11/2","maximum",ScalarType.Float}

            };
 
 public static Object[][]Zones =
 {{"1","heartrate",ScalarType.EmbeddedMessage}
        ,{"1/1","limits",ScalarType.EmbeddedMessage}
        ,{"1/1/1","low",ScalarType.Uint32}
        ,{"1/1/2","high",ScalarType.Uint32}
        ,{"1/2","duration",ScalarType.EmbeddedMessage}
        ,{"1/2/1","hours",ScalarType.Uint32}
        ,{"1/2/2","minutes",ScalarType.Uint32}
        ,{"1/2/3","seconds",ScalarType.Uint32}
        ,{"1/2.4","milliseconds",ScalarType.Uint32}
        ,{"2","power",ScalarType.EmbeddedMessage}
        ,{"2/1","limits",ScalarType.EmbeddedMessage}
        ,{"2/1/1","low",ScalarType.Uint32}
        ,{"2/1/2","high",ScalarType.Uint32}
        ,{"2/2","duration",ScalarType.EmbeddedMessage}
        ,{"2/2/1","hours",ScalarType.Uint32}
        ,{"2/2/2","minutes",ScalarType.Uint32}
        ,{"2/2/3","seconds",ScalarType.Uint32}
        ,{"2/2.4","milliseconds",ScalarType.Uint32}
        ,{"3","fatfit",ScalarType.EmbeddedMessage}
        ,{"3/1","limit",ScalarType.Uint32}
        ,{"3/2","fit-duration",ScalarType.EmbeddedMessage}
        ,{"3/2/1","hours",ScalarType.Uint32}
        ,{"3/2/2","minutes",ScalarType.Uint32}
        ,{"3/2/3","seconds",ScalarType.Uint32}
        ,{"3/2.4","milliseconds",ScalarType.Uint32}
        ,{"3/3","fat-duration",ScalarType.EmbeddedMessage}
        ,{"3/3/1","hours",ScalarType.Uint32}
        ,{"3/3/2","minutes",ScalarType.Uint32}
        ,{"3/3/3","seconds",ScalarType.Uint32}
        ,{"3/3.4","milliseconds",ScalarType.Uint32}
        ,{"4","speed",ScalarType.EmbeddedMessage}
        ,{"4/1","limits",ScalarType.EmbeddedMessage}
        ,{"4/1/1","low",ScalarType.Float}
        ,{"4/1/2","high",ScalarType.Float}
        ,{"4/2","duration",ScalarType.EmbeddedMessage}
        ,{"4/2/1","hours",ScalarType.Uint32}
        ,{"4/2/2","minutes",ScalarType.Uint32}
        ,{"4/2/3","seconds",ScalarType.Uint32}
        ,{"4/2.4","milliseconds",ScalarType.Uint32}
        ,{"4/3","distance",ScalarType.Float}
        ,{"10","heartrate-source",ScalarType.Enumerator}

            };


    
     public enum ScalarType {
        Unknown,
        Double,
        Float,
        Int32,
        Int64,
        Uint32,
        Uint64,
        Sint32,
        Sint64,
        Fixed32,
        Fixed64,
        Sfixed32,
        Sfixed64,
        Bool,
        String,
        Bytes,
        EmbeddedMessage, // Not actually a scalar value.
        Enumerator,      // Same as Int32.
        Group,           // Deprecated.
    };

    /// @see https://developers.google.com/protocol-buffers/docs/encoding#structure
    public enum WireType {
        Varint ( (byte)0),
        SixtyFourBit ((byte)1),
        LengthDelimeted ((byte)2),
        StartGroup ((byte)3),
        EndGroup ((byte)4),
        ThirtyTwoBit ((byte)5)
        ;
    private byte value;
    private WireType(byte value){this.value = value;}
    public byte getValue() {return value;}
        
    };
   
    
    
public static byte getWireType(ScalarType scalarType)
{
    switch (scalarType) {
    case Unknown:         break;
    case Double:          return WireType.SixtyFourBit.getValue();
    case Float:           return WireType.ThirtyTwoBit.getValue();
    case Int32:           return WireType.Varint.getValue();
    case Int64:           return WireType.Varint.getValue();
    case Uint32:          return WireType.Varint.getValue();
    case Uint64:          return WireType.Varint.getValue();
    case Sint32:          return WireType.Varint.getValue();
    case Sint64:          return WireType.Varint.getValue();
    case Fixed32:         return WireType.ThirtyTwoBit.getValue();
    case Fixed64:         return WireType.SixtyFourBit.getValue();
    case Sfixed32:        return WireType.ThirtyTwoBit.getValue();
    case Sfixed64:        return WireType.SixtyFourBit.getValue();
    case Bool:            return WireType.Varint.getValue();
    case String:          return WireType.LengthDelimeted.getValue();
    case Bytes:           return WireType.LengthDelimeted.getValue();
    case EmbeddedMessage: return WireType.LengthDelimeted.getValue();
    case Enumerator:      return WireType.Varint.getValue();
    case Group:           return WireType.StartGroup.getValue();
    }
 System.out.println("Types  653  scalarType Unknown oder Fehler ");  

    return WireType.Varint.getValue(); // There's really nothing sensible to return here :|
}


}
