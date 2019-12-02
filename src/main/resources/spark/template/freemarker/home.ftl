<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="refresh" content="5">
    <title >Web Checkers | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js"></script>

    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            text-align: left;
            padding: 8px;
        }
        tr:nth-child(even){
            background-color: #0F8A5F;
        }
        tr:nth-child(odd){
            background-color: #34A65F ;
        }

        th {
            color: white;
            background-color: #CC231E;
        }
        .button1{
            float: right;
            margin-top: 15px;
            border-radius: 10px;
            color: red;
            border: 2px solid #fccf4b;
            background-color: white;
            transition-duration: 0.4s;
            font-size: 16px;
            width: auto;
        }
        .button1:hover {
            background-color: #fccf4b;
            color: #34A65F;
        }
        .button2{
            border-radius: 10px;
            color: red;
            border: 2px solid #fccf4b;
            background-color: white;
            transition-duration: 0.4s;
            font-size: 16px;
            width: auto;
            display: inline-block;
        }
        /*fde396*/
        .button2:hover {
            background-color: #fccf4b;
            color: #34A65F;
        }


        .dropbtn {
            background-color: #4CAF50;
            color: white;
            padding: 12px;
            font-size: 16px;
            border: none;
            cursor: pointer;
        }

        .dropdown {
            float: left;
            display: inline-block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }

        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        .dropdown-content a:hover {background-color: #f1f1f1}

        .dropdown:hover .dropdown-content {
            display: block;
        }

        .dropdown:hover .dropbtn {
            background-color: #3e8e41;
        }

    </style>

    <div class="dropdown">
        <button class="dropbtn">Themes</button>
        <div class="dropdown-content">
            <button name="theme" value="Christmas" >Christmas</button>
            <button name="theme" value="David & Mansfield">David & Mansfield</button>
            <button name="theme" value="TacoTuesday">TacoTuesday</button>
        </div>
    </div>
</head>

<body>
<div class="page">

    <h1>Web Checkers | ${title}</h1>

    <!-- Provide a navigation bar -->
    <#include "nav-bar.ftl" />

    <div class="body">

        <!-- Provide a message to the user, if supplied. -->
        <#include "message.ftl" />
        <#--    if player signed in, display list-->
        <#if currentUser??>
            <div>
                <form id="postgame" action="/game" method="POST">
                    <h1 style="color: #fde396; font-weight: bolder; -webkit-text-stroke: .5px #34A65F; display: inline-block">Players Online </h1>
                    <button name="opponent" value="#iridocyclitis" type="submit" class= "button1">AI Opponent</button>
                </form>
            </div>

        <#--      there is no one online, 1 player means use are the only one in lobby-->
            <#if playerList?size == 1>
                <ul>
                    <li>There are no other players available to play at this time.</li>
                </ul>

            <#--      Other users online besides me, displays list make it clickable-->
            <#else>
                <table>
                    <tr>
                        <th>
                            PlayerName
                        </th>
                        <th>
                            Spectate
                        </th>
                    </tr>
                    <form id="postgame" action="/game" method="POST" >
                        <#list playerList as playerName>
                            <#if playerName != currentUser>
                                <tr>
                                    <td>
                                        <button class="button2" name="opponent" type="submit" value="${playerName.getName()}">${playerName.getName()}</button>
                                    </td>
                                    <td>
                                        <button class="button2" name="spectate" type="submit" value="${playerName.getName()}">Spectate ${playerName.getName()}</button>
                                    </td>
                                </tr>


                            </#if>
                        </#list>
                    </form>
                </table>
            </#if>

        <#else>
        <#--      Player is not signed in, can only see a number-->
            <h1 style="color: #fccc43; font-weight: bolder;   -webkit-text-stroke: .5px #34A65F; display: inline-block">Players Online </h1>
            <ul>
                <li>There are: ${playerList?size} online</li>
            </ul>

        </#if>



        <!-- TODO: future content on the Home:
                to start games,
                spectating active games,lo
                or replay archived games
        -->


    </div>

</div>
</body>



</html>
