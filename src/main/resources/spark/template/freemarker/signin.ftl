<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

    <style>
        .button1{
            border-radius: 10px;
            color: red;
            border: 2px solid #fccf4b;
            background-color: white;
            transition-duration: 0.4s;
            font-size: 16px;
            width: 15%;
        }
        .button1:hover {
            background-color: #fccf4b;
            color: #34A65F;
        }


    </style>
<#--    <meta http-equiv="refresh" content="10">-->
    <title>Web Checkers | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

    <h1>Web Checkers | ${title}</h1>

     <!-- Provide a navigation bar -->
    <#include "nav-bar.ftl" />



    <div class="body">

         <form id="signin" action="/signin" method="POST">
<#--             <a href="#" onclick="event.preventDefault(); sign.submit();">sign in [${currentUser.name}]</a>-->
             <br>
             <input name="username"/>
             <br/><br/>
             <button class="button1" type="submit">Sign In</button>
         </form>
         <!-- Provide a message to the user, if supplied. -->
         <#include "message.ftl" />

         <!-- TODO: future content on the Home:
                 to start games,
                 spectating active games,
                 or replay archived games
         -->




    </div>

</div>
</body>

</html>

