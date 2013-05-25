var setupEditor = function(ace){
    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");
    editor.getSession().setMode("ace/mode/java");
};


require.config({ paths: {ace: "js/ace/ace/lib/ace"} });
require(["ace/ace"], setupEditor);
