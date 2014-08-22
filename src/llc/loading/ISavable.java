package llc.loading;

import de.teamdna.databundle.DataBundle;

public interface ISavable {

	DataBundle writeToDataBundle();

	void readFromDataBundle(DataBundle data);

}
