package branches.setup;

import com.darjow.framework.script.decisiontree.Branch;

public class SetupBranch extends Branch {

    public SetupBranch(){}


    @Override
    public String getStatus() {
        return "[Branch] Setting up before splashing.";
    }
}
