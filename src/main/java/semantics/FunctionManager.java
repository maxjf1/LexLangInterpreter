/*
  Maxwell Souza 201435009
  Rodolpho Rossete 201435032
 */

package semantics;

import lexlang.LexLangParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FunctionManager {
    final HashMap<String, List<FunctionDeclaration>> functions = new HashMap<>();

    public HashMap<String, List<FunctionDeclaration>> getFunctions() {
        return functions;
    }

    public FunctionDeclaration addFunction(LexLangParser.FuncContext ctx) {
        FunctionDeclaration f = new FunctionDeclaration(ctx);

        if (!functions.containsKey(f.getId())) {
            functions.put(f.getId(), new ArrayList<>());
        }
        List<FunctionDeclaration> functionList = functions.get(f.getId());

        for (FunctionDeclaration declaration : functionList) {
            if (declaration.getArguments().size() != f.getArguments().size()) continue;
            boolean error = true;
            for (int i = 0; i < declaration.getArguments().size(); i++) {
                if (!declaration.getArguments().get(i).type.getText().equals(f.getArguments().get(i).type.getText())) {
                    error = false;
                    break;
                }
            }
            if (error)
                throw new LangException("Function '" + f.getId() + "' already has been declared with the same signature", ctx);

        }

        functions.get(f.getId()).add(f);

        return f;
    }

    public FunctionDeclaration getFunction(String name) {
        if (!functions.containsKey(name)) throw new LangException("Function '" + name + "' not found");
        for (FunctionDeclaration functionDeclaration : functions.get(name))
            if (functionDeclaration.getArguments().size() == 0)
                return functionDeclaration;

        return functions.get(name).get(0);
    }

    public List<FunctionDeclaration> getFunctions(String name) {
        if (!functions.containsKey(name)) throw new LangException("Function '" + name + "' not found");
        return functions.get(name);
    }
}
