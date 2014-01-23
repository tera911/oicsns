/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    game.ws.sendJSON = function(obj) {
        var json = JSON.stringify(obj);
        this.send(json);
    };
});