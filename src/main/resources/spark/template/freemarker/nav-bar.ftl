 <div class="navigation">
  <#if currentUser??>
    <form id="home" action="/" method="post">
        <a href="/" onclick="event.preventDefault(); home.submit();">my home</a>
    </form> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.getName()}]</a>
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
