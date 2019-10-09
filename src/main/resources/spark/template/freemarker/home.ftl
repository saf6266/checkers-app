<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<#--  <meta http-equiv="refresh" content="10">-->
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <h2 style="color: blue">Players Online </h2>
<#--    if player signed in, display list-->
    <#if currentUser??>
<#--      there is no one online, 1 player means use are the only one in lobby-->
      <#if playerList?size == 1>
          <ul>
            <li>There are no other players available to play at this time.</li>
          </ul>
      </#if>
    <#else>
<#--      Player is not signed in, can only see a number-->
      <ul>
        <li>There are: ${playerList?size} online</li>
      </ul>

    </#if>

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->




  </div>

</div>
</body>

</html>
