/*
    Plus x android javascript sdk v1.0
    @Author DenoGeek~kariukidennisde@gmail.com
*/


var plusx_items = new Array();
var header_text = "The business name";

function setBusinessName(name){
    /** 
    * Summary. (updates the receipt header)
    *@access public
    *@return {void} 
    */
    AndroidInterface.updateBusinessName(name)
}

function addItem(item){
    /** 
    * Summary. (adds an item to the printable array)
    *@access public
    *@Param {print_itme} item
    *@return {boolean} true
    */
    plusx_items.push(item)
    return true;
}


function getItems(){
    /** 
    * Summary. (returns printable array)
    *@access public
    *@return {Array} []
    */
    return plusx_items;
}

function getPrintsJson(){
    /** 
    * Summary. (returns json parsable array)
    *@access public
    *@return {JSON} []
    */
    return JSON.stringify(getItems())
}

function printDummyPusx(){
    /** 
    * Summary. (prints a preformated receipt)
    *@access public
    *@return {void} []
    */
    AndroidInterface.printDummyReceipt()
}

function plusxPrint(total,tendered_in){
    /** 
    * Summary. (sends the plusx_items array to the android interface)
    *@access public
    *@return {boolean} true/false
    */
    var response = null; 
    try{
        response = AndroidInterface.printPlusxReceipt(getPrintsJson(),total,tendered_in)
    }catch(err){
        return {"errors":err,"response":response}
    }
    
    return {"errors":null,"response":response}
}