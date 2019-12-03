 <div class="navigation">
     <script type="text/javascript" src="js/game/LetItSnow.js"></script>
     <script type="text/javascript">
         snowStorm.snowColor = 'white'; // blue-ish snow!?
         snowStorm.flakesMaxActive = 1000;  // show more snow on screen at once
         snowStorm.useTwinkleEffect = true; // let the snow flicker in and out of view
     </script>
  <#if currentUser??>
    <form id="home" action="/" method="post" style="display:inline-block">
        <a href="/" onclick="event.preventDefault(); home.submit();">my home</a>
    </form> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.getName()}]</a>
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
