<div id="tabsContainer">
	<ul>
	    <li><a href="#tab0">Bots</a></li>
	    <li><a href="#tab1">Jobs</a></li>
	    <li><a href="#tab2">Tasks</a></li>
	    <li><a href="#tab3">Runs</a></li>
	    <li><a href="#tab4">Events</a></li>
	    <li><a href="#tab5">Servers</a></li>
	  </ul>
	<div id="tab0"><table id="botsList"></table><div id="botsPager"></div></div>
	<div id="tab1"><table id="jobsList"></table><div id="jobsPager"></div></div>
	<div id="tab2"><table id="tasksList"></table><div id="tasksPager"></div></div>
	<div id="tab3"><table id="runsList"></table><div id="runsPager"></div></div>
	<div id="tab4"><table id="eventsList"></table><div id="eventsPager"></div></div>
	<div id="tab5"><table id="serversList"></table><div id="serversPager"></div></div>
</div>
<input type="hidden" id="botid" value="">
<input type="hidden" id="jobid" value="">
<input type="hidden" id="taskid" value="">
<input type="hidden" id="runid" value="">
<div id="dialog" title="Dump job data">
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Dump Data(CSV)</a></li>
			<!--li><a href="#tabs-2">Dump Schema</a></li-->
		</ul>
		<div id="tabs-1">
			<form id="form-dump-data" method="GET" action="">
			
			<div>
				<p>
					<input id="dgexport" value="Execute Script" />
					<img id="loading" src="./sites/all/modules/bots/image/loading.gif" style="display:none;">
				</p>
				<p><input id="dgdata" value="Check exported files" /></p>
				<p>
				  <ul id="datalist"></ul>
			    </p>
			</div>
			<!--div class="disabled">
				<p><label for="datefrom">From:</label><input type="text" id="datefrom" name="datefrom"></p>
				<p><label for="dateto">&nbsp;To&nbsp;&nbsp;&nbsp;:</label><input type="text" id="dateto" name="dateto"></p>
			</div-->
			
			</form>
		</div>
		<!--div id="tabs-2">
			<p><input id="dgschema" value="Dump Schema" /></p>		
		</div-->
	</div>
</div>
<div class="contextMenu" id="menuServer">
		<ul>
			<li id="menuServerView"><span>View Status</span></li>
			<li id="menuServerEnable"><span>(En/Dis)able</span></li>
		</ul>
</div>
<div class="contextMenu" id="menuBot">
		<ul>
			<li id="menuBotEnable"><span>(En/Dis)able</span></li>
			<li id="menuBotClearEvents"><span>Clear Bot Events</span></li>
			<li id="menuBotClearRuns"><span>Clear Bot Runs</span></li>
			<li id="menuBotClearTasks"><span>Clear Bot Tasks</span></li>
			<li id="menuBotClearJobs"><span>Clear Bot Jobs</span></li>
			<li id="menuBotClearAllEvents"><span>Clear All Events</span></li>
			<li id="menuBotClearAllRuns"><span>Clear All Runs</span></li>
		</ul>
</div>
<div id="upload_container" style="display:none">
	<form>
		<div id="queue"></div>
		<input id="file_upload" name="file_upload" type="file" multiple="true">
		<input type="hidden" id="h_jid" value=0 />
	</form>
</div>
<div class="contextMenu" id="menuJob">
		<ul>
			<li id="menuJobUpload"><span>Upload</span></li>
			<li id="menuJobEnable"><span>(En/Dis)able</span></li>
			<li id="menuJobClearEvents"><span>Clear Job Events</span></li>
			<li id="menuJobClearRuns"><span>Clear Job Runs</span></li>
			<li id="menuJobClearTasks"><span>Clear Job Tasks</span></li>
			<li id="menuJobStart"><span>Start</span></li>
		</ul>
</div>
<div class="contextMenu" id="menuTask">
		<ul>
			<li id="menuTaskEnable"><span>(En/Dis)able</span></li>
			<li id="menuTaskStart"><span>Start</span></li>
			<li id="menuTaskStop"><span>Stop</span></li>
			<li id="menuTaskClearEvents"><span>Clear Task Events</span></li>
			<li id="menuTaskClearRuns"><span>Clear Task Runs</span></li>
		</ul>
</div>