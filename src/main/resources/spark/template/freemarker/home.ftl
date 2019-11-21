<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="refresh" content="5">
    <title>Web Checkers | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
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
            background-color: #ffa321;
        }
        tr:nth-child(odd){
            background-color: #FFC175 ;
        }

        th {
            color: #A2E5DE;
            background-color: #F9A43B;
        }
        .button1{
            float: right;
            margin-top: 15px;
            border-radius: 10px;
            color: #73BFB8;
            border: 2px solid #ffa321;
            background-color: white;
            transition-duration: 0.4s;
            font-size: 16px;
            width: auto;
        }
        .button1:hover {
            background-color: #ffa321;
            color: white;
        }
        .button2{
            border-radius: 10px;
            color: #73BFB8;
            border: 2px solid #ffa321;
            background-color: white;
            transition-duration: 0.4s;
            font-size: 16px;
            width: 15%;
            display: inline-block;
        }
        .button2:hover {
            background-color: #ffa321;
            color: white;
        }

    </style>
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
                    <h2 style="color: mediumseagreen; display: inline-block">Players Online </h2>
                    <button name="opponent" value="iridocyclitis" type="submit" class= "button1">AI Opponent</button>
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
                    </tr>
                    <form id="postgame" action="/game" method="POST" >
                        <#list playerList as playerName>
                            <#if playerName != currentUser>
                                <tr>
                                    <td>
                                        <button class="button2" name="opponent" type="submit" value="${playerName.getName()}">${playerName.getName()}</button>
                                        <button class="button2" name="spectate" type="submit" value="${playerName.getName()}">Spectate Game</button>
                                        <#--                         <a href="/game" onclick="event.preventDefault(); postgame.submit();"> ${playerName.getName()}  </a>-->
                                    </td>
                                </tr>
                            </#if>
                        </#list>
                    </form>
                </table>
            </#if>

        <#else>
        <#--      Player is not signed in, can only see a number-->
            <h2 style="color: lightgreen; display: inline-block">Players Online </h2>
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
