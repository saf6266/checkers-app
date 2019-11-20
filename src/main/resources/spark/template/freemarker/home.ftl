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
            background-color: #FFDDA1;
        }
        tr:nth-child(odd){
            background-color: #DFFDFF ;
        }

        th {
            color: azure;
            background-color: #223843;
        }

        .button1{
            float: right;
            margin-top: 15px;
            border-radius: 12px;
            color: Black;
            border: 2px solid #FFD487;
            background-color: white;
            transition-duration: 0.4s;
            font-size: 16px;
        }
        .button1:hover {
            background-color: #FFD487;
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
                <h2 style="color: blue; display: inline-block">Players Online </h2>
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
                       <input name="opponent" type="submit" value="${playerName.getName()}">
                         <a href="/game" onclick="event.preventDefault(); postgame.submit();"> ${playerName.getName()}  </a>
                     </td>
                   </tr>
                  </#if>
                </#list>
            </form>
        </table>
      </#if>

    <#else>
<#--      Player is not signed in, can only see a number-->
        <h2 style="color: blue; display: inline-block">Players Online </h2>
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
