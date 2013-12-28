/*
General Todo: 
- explore ace's tokenizer
*/


// globals. TODO: put in another file and rquire.js it
var Orange = {}; // globals
var Orange.config = {COMPILER_INTERVAL : 1000};


// init. run: put in another file
function setup(editSession)
{
    Orange.doc = editSession.getDocument();

    // TODO: do this on doc change of the document using the ace library instead
    Orange.compilerIntervalId = setInterval(compile, Orange.config.COMPILER_INTERVAL);
    Orange.parse = javaParser.prase;
}

function compile()
{
    // get text
    var val = Orange.doc.getValue();

    // parse
    var results; 
    try
    {
	results = Orange.parse(val);
    }
    // TODO: see if we can differentiate jison and other/general errors
    catch(e) // TODO: investigate way to improve perf hit from hitting catch repeatedly
    {
        results = e.toString();
    }

   // show results
    $("#console").text(results);

}

// init. TODO: put in another file
var init = function(ace){
    // init editor
    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");

    var editSession = editor.getSession();
    editSession.setMode("ace/mode/java");
    setup(editSession);
};

require.config({ 
    paths: {
        ace: "js/ace/ace"
    },
    waitSeconds: 60
});
require(["ace/ace"], init);
