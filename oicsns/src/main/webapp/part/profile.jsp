<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="true"%>
<div id="profile">
    <form id="userdata">
        <div class="left_form"><!-- プロフィール入力欄 -->

            <div id="hogehoge1"><label for="student_id">学籍番号</label>
                <input type="text" id="student_id" name="student_id" value="<%= session.getAttribute("studentNumber") %>" disabled="disabled" tabindex="1"></div>
            <div id="hogehoge1"><label for="screenname">ハンドルネーム</label>
                <input type="text" id="username" name="username" value="" tabindex="2">
                <input type="button" id="confirm" value="重複確認" tabindex="3"></div>

            <div id="hogehoge1"><label for="grade">学年</label>
                <select tabindex="4" id="grade" name="grade">
                    <option value="" selected>--</option>
                    <option value="1">1年</option>
                    <option value="2">2年</option>
                    <option value="3">3年</option>
                    <option value="4">4年</option>
                </select>
                <input id="hidegrade" class="hidebutton" type="checkbox" name="" value="1"/>
                <label id="hidegradelabel" for="hidegrade">非表示</label>
            </div>
            <div id="hogehoge1">
                <label>性別</label>
                <div id="radiolist">
                    <label class="radiolabel">
                        <input type="radio" name="sex" value="1" tabindex="5">男</label>
                    <label class="radiolabel">
                        <input type="radio" name="sex" value="2">女</label><br>
                    <label class="radiolabel">
                        <input type="radio" name="sex" value="3">男の娘</label>
                    <label class="radiolabel">
                        <input type="radio" name="sex" value="4">その他</label>
                </div>
                <input id="hidegender" class="hidebutton" type="checkbox" name="" value="1"/>
                <label id="hidegenderlabel" for="hidegender">非表示</label>
            </div>
            <div id="hogehoge1">
                <label for="year">生年月日</label>
                <select id="birthday_year" name="birthday_year" tabindex="6">
                    <option value="" selected>----</option>
                    <option value="1930">1930年</option>
                    <option value="1931">1931年</option>
                    <option value="1932">1932年</option>
                    <option value="1933">1933年</option>
                    <option value="1934">1934年</option>
                    <option value="1935">1935年</option>
                    <option value="1936">1936年</option>
                    <option value="1937">1937年</option>
                    <option value="1938">1938年</option>
                    <option value="1939">1939年</option>
                    <option value="1940">1940年</option>
                    <option value="1941">1941年</option>
                    <option value="1942">1942年</option>
                    <option value="1943">1943年</option>
                    <option value="1944">1944年</option>
                    <option value="1945">1945年</option>
                    <option value="1946">1946年</option>
                    <option value="1947">1947年</option>
                    <option value="1948">1948年</option>
                    <option value="1949">1949年</option>
                    <option value="1950">1950年</option>
                    <option value="1951">1951年</option>
                    <option value="1952">1952年</option>
                    <option value="1953">1953年</option>
                    <option value="1954">1954年</option>
                    <option value="1955">1955年</option>
                    <option value="1956">1956年</option>
                    <option value="1957">1957年</option>
                    <option value="1958">1958年</option>
                    <option value="1959">1959年</option>
                    <option value="1960">1960年</option>
                    <option value="1961">1961年</option>
                    <option value="1962">1962年</option>
                    <option value="1963">1963年</option>
                    <option value="1964">1964年</option>
                    <option value="1965">1965年</option>
                    <option value="1966">1966年</option>
                    <option value="1967">1967年</option>
                    <option value="1968">1968年</option>
                    <option value="1969">1969年</option>
                    <option value="1970">1970年</option>
                    <option value="1971">1971年</option>
                    <option value="1972">1972年</option>
                    <option value="1973">1973年</option>
                    <option value="1974">1974年</option>
                    <option value="1975">1975年</option>
                    <option value="1976">1976年</option>
                    <option value="1977">1977年</option>
                    <option value="1978">1978年</option>
                    <option value="1979">1979年</option>
                    <option value="1980">1980年</option>
                    <option value="1981">1981年</option>
                    <option value="1982">1982年</option>
                    <option value="1983">1983年</option>
                    <option value="1984">1984年</option>
                    <option value="1985">1985年</option>
                    <option value="1986">1986年</option>
                    <option value="1987">1987年</option>
                    <option value="1988">1988年</option>
                    <option value="1989">1989年</option>
                    <option value="1990">1990年</option>
                    <option value="1991">1991年</option>
                    <option value="1992">1992年</option>
                    <option value="1993">1993年</option>
                    <option value="1994">1994年</option>
                    <option value="1995">1995年</option>
                    <option value="1996">1996年</option>
                    <option value="1997">1997年</option>
                    <option value="1998">1998年</option>
                    <option value="1999">1999年</option>
                    <option value="2000">2000年</option>
                    <option value="2001">2001年</option>
                    <option value="2002">2002年</option>
                    <option value="2003">2003年</option>
                    <option value="2004">2004年</option>
                    <option value="2005">2005年</option>
                    <option value="2006">2006年</option>
                    <option value="2007">2007年</option>
                    <option value="2008">2008年</option>
                    <option value="2009">2009年</option>
                    <option value="2010">2010年</option>
                    <option value="2011">2011年</option>
                    <option value="2012">2012年</option>
                    <option value="2013">2013年</option>
                </select>
                <select id="birthday_month" name="birthday_month" tabindex="7">
                    <option value="" selected>--</option>
                    <option value="01">1月</option>
                    <option value="02">2月</option>
                    <option value="03">3月</option>
                    <option value="04">4月</option>
                    <option value="05">5月</option>
                    <option value="06">6月</option>
                    <option value="07">7月</option>
                    <option value="08">8月</option>
                    <option value="09">9月</option>
                    <option value="10">10月</option>
                    <option value="11">11月</option>
                    <option value="12">12月</option>
                </select>
                <select id="birthday_day" name="birthday_day" tabindex="8">
                    <option value="" selected>--</option>
                    <option value="01">1日</option>
                    <option value="02">2日</option>
                    <option value="03">3日</option>
                    <option value="04">4日</option>
                    <option value="05">5日</option>
                    <option value="06">6日</option>
                    <option value="07">7日</option>
                    <option value="08">8日</option>
                    <option value="09">9日</option>
                    <option value="10">10日</option>
                    <option value="11">11日</option>
                    <option value="12">12日</option>
                    <option value="13">13日</option>
                    <option value="14">14日</option>
                    <option value="15">15日</option>
                    <option value="16">16日</option>
                    <option value="17">17日</option>
                    <option value="18">18日</option>
                    <option value="19">19日</option>
                    <option value="20">20日</option>
                    <option value="21">21日</option>
                    <option value="22">22日</option>
                    <option value="23">23日</option>
                    <option value="24">24日</option>
                    <option value="25">25日</option>
                    <option value="26">26日</option>
                    <option value="27">27日</option>
                    <option value="28">28日</option>
                    <option value="29">29日</option>
                    <option value="30">30日</option>
                    <option value="31">31日</option>
                </select>
                <input id="hidebirthday" class="hidebutton" type="checkbox" name="" value="1"/>
                <label id="hidebirthdaylabel" for="hidebirthday">非表示</label>
            </div>

            <div id="hogehoge1"><label for="comment">コメント</label>
                <textarea id="comment" name="comment" maxlength="150" rows="4" cols="30" tabindex="9" placeholder="コメントを60文字前後で入力してください。"></textarea>
            </div>
            <div id="hogehoge1" class="message">
            </div>

        </div>
        <div class="right_form"> <!-- アバター選択・送信 -->
            <div id="avatar_window" class="jscrollpane">	<!-- 仮画像 -->
                
            </div>
            <div id="avatar_bg">
            </div>
            <button id="sendbutton" name="register" type="submit">登録</button>	
        </div>	
    </form>
</div>