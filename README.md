# Java-RestAPI
BASE.ADDR = "RestAPI"

GET /BASE/Servlet/Key (Returns JSONObject {"key":"...", "value":"..."}) "\n"
PUT /BASE/Servlet/Key (It sends new value (In JSON format) for the given key in order to update it) \n
POST /BASE/Servlet (creates new record in HashMap {"key":"...", "value":"..."} pair in JSON format) \n 
DELETE /Base/Servlet/Key (delete "key" - "value" pair record by given Key) \n
