<@bravo.Page name="performanceInspect" title="性能监控">
	<@bravo.Viewport  layout="border">
	 
		<@bravo.Portal  region="center" margins="35 5 5 0" name="center"  > 
			<@bravo.PortalColumn columnWidth=".3333" name="portal1" title="portal1" style="padding:10px 0 10px 10px"> 
				<@bravo.Portlet autoLoad="../report/performanceInspect!chartMem.action" title="内存监测" /> 
			</@bravo.PortalColumn>
			<@bravo.PortalColumn columnWidth=".3333" name="portal2" title="portal2" style="padding:10px 0 10px 10px"> 
				<@bravo.Portlet autoLoad="../report/performanceInspect!chartCpu.action" title="CPU监测"/> 
			</@bravo.PortalColumn>
			<@bravo.PortalColumn columnWidth=".3333" name="portal3" title="portal3" style="padding:10px 0 10px 10px"> 
				<@bravo.Portlet autoLoad="../report/performanceInspect!chartDisk.action" title="硬盘监测"/> 
			</@bravo.PortalColumn>
		</@bravo.Portal>	
		 
	 
	</@bravo.Viewport>
</@bravo.Page>
