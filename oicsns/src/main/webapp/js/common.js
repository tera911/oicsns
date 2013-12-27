$(function(){
    Number.prototype.fillZero = function(n){
    var r = this.toString().split('');
    while(r.length < n){
        r.unshift('0');
    }
    return r.join('');
};
    Array.prototype.clone = function(){
        return Array.apply(null, this);
    };
});
    function is(type, obj) {
    var clas = Object.prototype.toString.call(obj).slice(8, -1);
    return obj !== undefined && obj !== null && clas === type;
}