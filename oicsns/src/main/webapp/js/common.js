$(function(){
    Number.prototype.fillZero = function(n){
    var r = this.toString().split('');
    while(r.length < n){
        r.unshift('0');
    }
    return r.join('');
};
    
});
