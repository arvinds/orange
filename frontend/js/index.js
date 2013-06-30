function pollEditor(mainDoc)
{
    var arr = [mainDoc.getValue()];
    alert("compile program");
}

function run(editSession)
{
    var mainDoc = editSession.getDocument();
    var timeoutId = setInterval(function(){ pollEditor(mainDoc); }, 1000);
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
