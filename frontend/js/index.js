function pollEditor(mainDoc)
{
    var arr = [mainDoc.getValue()];
    var errors = document.consoleApplet.compileProgram(arr);
    if(errors.length == 0)
    {
        document.consoleApplet.setText("No errors were found!");
    }
    else
    {
        document.consoleApplet.setText("The following errors were found!\n");
        document.consoleApplet.append("errors: " + errors[0]);
    }
}

function run(editSession)
{
    var mainDoc = editSession.getDocument();
    var timeoutId = setInterval(function(){ pollEditor(mainDoc); }, 100);
}

var setupEditor = function(ace){
    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");

    var editSession = editor.getSession();
    editSession.setMode("ace/mode/java");
    run(editSession);
};


require.config({ 
    paths: {
        ace: "js/ace/ace/lib/ace"
    },
    waitSeconds: 60
});
require(["ace/ace"], setupEditor);
