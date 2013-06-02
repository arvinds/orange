function pollEditor(mainDoc)
{
    var arr = [mainDoc.getValue()];
//    document.consoleApplet.compileProgram("asf");
    document.consoleApplet.setText("asf");
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


require.config({ paths: {ace: "js/ace/ace/lib/ace"} });
require(["ace/ace"], setupEditor);
