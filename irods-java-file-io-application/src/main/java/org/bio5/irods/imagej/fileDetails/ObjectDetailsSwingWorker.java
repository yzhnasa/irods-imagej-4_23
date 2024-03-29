package org.bio5.irods.imagej.fileDetails;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;
import org.bio5.irods.imagej.bean.IrodsImageJBean;
import org.bio5.irods.imagej.utilities.Constants;
import org.bio5.irods.imagej.utilities.IrodsUtilities;
import org.bio5.irods.imagej.views.DirectoryContentsPane;
import org.irods.jargon.core.pub.IRODSFileSystemAOImpl;
import org.irods.jargon.core.pub.domain.ObjStat;

public class ObjectDetailsSwingWorker extends SwingWorker<Void, Integer> {

	private IrodsImageJBean irodsImageJ;
	private String filePathToGetObjStat;
	private IRODSFileSystemAOImpl iRODSFileSystemAOImpl;

	public ObjectDetailsSwingWorker(IrodsImageJBean irodsImageJ) {
		super();
		this.irodsImageJ = irodsImageJ;
	}

	/* Logger instantiation */
	static Logger log = Logger.getLogger(ObjectDetailsSwingWorker.class
			.getName());

	@Override
	protected Void doInBackground() throws Exception {

		if (null != irodsImageJ.getiRODSFileSystemAOImpl()) {
			filePathToGetObjStat = irodsImageJ.getObjSelectedUsingSingleClick();
			if (irodsImageJ.isHomeDirectoryTheRootNode()) {
				filePathToGetObjStat = IrodsUtilities.getPathSeperator()
						+ irodsImageJ.getIrodsAccount().getZone()
						+ filePathToGetObjStat;
			} else {
				filePathToGetObjStat = IrodsUtilities.getPathSeperator()
						+ irodsImageJ.getIrodsAccount().getZone()
						+ IrodsUtilities.getPathSeperator() + Constants.HOME
						+ filePathToGetObjStat;
			}
			filePathToGetObjStat = filePathToGetObjStat.replace('\\', '/');
			log.info("filePathToGetObjStat: " + filePathToGetObjStat);
			iRODSFileSystemAOImpl = irodsImageJ.getiRODSFileSystemAOImpl();
			ObjStat objStatForGivenAbsolutePath = iRODSFileSystemAOImpl
					.getObjStat(filePathToGetObjStat);
			irodsImageJ
					.setObjStatForGivenAbsolutePath(objStatForGivenAbsolutePath);

		} else {
			log.error("iRODSFileSystemAOImpl or ObjSelectedUsingSingleClick() is empty");
		}
		return null;
	}

	@Override
	public void done() {
		DirectoryContentsPane dcp = irodsImageJ.getDirectoryContentsPane();
		dcp.setFileInformationFromObjStat(irodsImageJ
				.getObjStatForGivenAbsolutePath());
	}

}
