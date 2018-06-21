/**
 * Yahoo路線情報表示
 * @param {type} from
 * @param {type} to
 * @returns {undefined}
 */
function callYahooTransit(from, to) {
    if (!from || !to) { 
        alert('出発地と到着地を入力してください。');
        return;
    }
    var url = "https://transit.yahoo.co.jp/search/result"
                + "?from=" + encodeURIComponent(from)
                + "&to=" + encodeURIComponent(to)
                + "&ticket=ic&shin=1&ex=1&al=1&hb=1&lb=1&sr=1&type=5&s=1&ei=utf-8";
    var option = "width=800, height=600, menubar=no, toolbar=no, location=no, status=no, resizable=yes, scrollbars=yes";
    window.open(url, null, option);
}


