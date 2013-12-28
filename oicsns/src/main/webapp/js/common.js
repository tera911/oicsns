$(function() {
    Number.prototype.fillZero = function(n) {
        var r = this.toString().split('');
        while (r.length < n) {
            r.unshift('0');
        }
        return r.join('');
    };
    Array.prototype.clone = function() {
        return Array.apply(null, this);
    };
});
function is(type, obj) {
    var clas = Object.prototype.toString.call(obj).slice(8, -1);
    return obj !== undefined && obj !== null && clas === type;
}
function wait(flag, callback, timer) {
    var _flag = flag;
    var _callback = callback;
    var _timer = timer;
    timerID = setInterval(function() {
        if (eval(_flag)) {
            clearInterval(timerID);
            timerID = null;
            _callback();
        }
    }, _timer);
}
;
   