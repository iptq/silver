package edu.umn.cs.melt.ide.wizard;

import java.io.ByteArrayInputStream;
import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import edu.umn.cs.melt.ide.imp.builders.Nature;
import edu.umn.cs.melt.ide.impl.SVRegistry;

/**
 * The New Project Wizard is used to create a project. In general
 * it follows these steps:
 * <p>
 * (1) show a page for user to name the project <br>
 * (2) create a project folder with the given name in current workspace <br>
 * (3) create a property file <br>
 * (4) add the nature to the project (note nature is associated with builder) <br>
 */
public class NewProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

	private WizardNewProjectCreationPage page1;
	
	private IConfigurationElement configElement;
	
	public NewProjectWizard() {
		setWindowTitle(SVRegistry.get().name());
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}
	
	@Override
	public void addPages() {
	    super.addPages();
	    
	    String name = SVRegistry.get().name();

	    page1 = new WizardNewProjectCreationPage("New " + name + " Project Wizard");
	    page1.setTitle(name + " Project");
	    page1.setDescription("Create new " + name + " project");

	    addPage(page1);
	}

	@Override
	public boolean performFinish() {
	    //Get user's input
	    String name = page1.getProjectName();
	    URI location = null;
	    if (!page1.useDefaults()) {
	        location = page1.getLocationURI();
	    }
	    
	    //Create project in current workspace
	    createProject(name, location);
	    
	    //Update perspective
	    BasicNewProjectResourceWizard.updatePerspective(configElement);

	    return true;
	}
	
	@Override
	public void setInitializationData(
	    IConfigurationElement config, String propertyName, Object data) throws CoreException {
	    configElement = config;
	}

    /**
     * Create a project and add nature to it.
     *
     * @param location
     * @param projectName
     */
    private void createProject(String projectName, URI location) {
        //Get the project handle by given name
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

        if (!project.exists()) {
            //Set the description
            URI projectLocation = location;
            IProjectDescription desc = 
            	project.getWorkspace().newProjectDescription(project.getName());
            if (location != null && 
            	location.equals(ResourcesPlugin.getWorkspace().getRoot().getLocationURI())) {
                projectLocation = null;
            }
            desc.setLocationURI(projectLocation);
            
            //Create and open the project
            try {
                project.create(desc, null);
                if (!project.isOpen()) {
                    project.open(null);
                }
                
                //Create properties file
            	project.getFile("project.properties").create(
            		new ByteArrayInputStream(SVRegistry.get().getInitialProjectProperties().getBytes()), true, null);
            } catch (CoreException e) {
		    	page1.setErrorMessage(e.getMessage());
		    	e.printStackTrace();
		    	return;
            }
        }
        
        //Add the nature, if not added yet
        try {
            Nature.addToProject(project, SVRegistry.get().getNatureId());
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

}

