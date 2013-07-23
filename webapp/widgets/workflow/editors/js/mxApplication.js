/*
 * $Id: mxApplication.js,v 1.13 2008/07/14 14:58:14 gaudenz Exp $
 * Copyright (c) 2006, Gaudenz Alder
 *
 * Defines the startup sequence of the application.
 *
 */
{

	/**
	 * Constructs a new application
	 */
	function mxApplication(config)
	{
		var hideSplash = function()
		{
			// Fades-out the splash screen
			var splash = document.getElementById('splash');
			
			if (splash != null)
			{
				try
				{
					mxUtils.release(splash);
					mxUtils.fadeOut(splash, 100, true);
				}
				catch (e)
				{
					splash.parentNode.removeChild(splash);
				}
			}
		}
		
		try
		{
			if (!mxClient.isBrowserSupported())
			{
				mxUtils.error('Browser is not supported!', 200, false);
			}
			else
			{
				var node = mxUtils.load(config).getDocumentElement();
				var editor = new mxEditor(node);
				
				// Updates the window title after opening new files
				var title = document.title;
				var funct = function(sender)
				{
					document.title = title + ' - ' + sender.getTitle();
				};
				
				editor.addListener('open', funct);
				
				// Prints the current root in the window title if the
				// current root of the graph changes (drilling).
				editor.addListener('root', funct);
				funct(editor);
				
				// Displays version in statusbar
				editor.setStatus('mxGraph '+mxClient.VERSION);
				
				// Shows the application
				hideSplash();
			}
		}
		catch (e)
		{
			hideSplash();

			// Shows an error message if the editor cannot start
			mxUtils.alert('Cannot start application: '+e.message);
			throw e; // for debugging
		}
								
		return editor;
	}

}
