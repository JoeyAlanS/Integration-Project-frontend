[Setup]
AppId={{Integration-Project-Identifier}}
AppName=Integration-Project-Frontend-1.0
AppVersion=1.0
AppVerName=Integration-Project-Frontend-1.0
DefaultDirName={localappdata}\Integration-Project-Frontend-1.0
DefaultGroupName=Integration-Project-Frontend
DisableReadyPage=Yes
Compression=lzma2
OutputDir=package
OutputBaseFileName=Integration_Project_Frontend_1.0
SolidCompression=yes
UninstallDisplayIcon={app}\Integration-Project-Frontend-1.0.ico
UninstallDisplayName=Uninstall
SetupIconFile=Integration-Project-Frontend-1.0/Integration-Project-Frontend-1.0.ico
WizardImageStretch=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "portuguese"; MessagesFile: "compiler:Languages\BrazilianPortuguese.isl"

[Files]
Source: "Integration-Project-Frontend-1.0\Integration-Project-Frontend-1.0.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "Integration-Project-Frontend-1.0\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:additionalIcons}"; Flags: unchecked

[Icons]
Name: "{group}\Integration-Project-Frontend-1.0"; Filename: "{app}\Integration-Project-Frontend-1.0.exe"; WorkingDir: "{app}"; IconFilename: "{app}\Integration-Project-Frontend-1.0.ico"
Name: "{commondesktop}\Integration-Project-Frontend-1.0"; Filename: "{app}\Integration-Project-Frontend-1.0.exe"; WorkingDir: "{app}"; IconFilename: "{app}\Integration-Project-Frontend-1.0.ico"
Name: "{group}\{cm:UninstallProgram,Integration-Project-Frontend-1.0}"; Filename: "{uninstallexe}";IconFilename: "{app}\Integration-Project-Frontend-1.0.ico"

[Run]
Filename: "{app}\Integration-Project-Frontend-1.0.exe"; Description: "{cm:LaunchProgram,Integration-Project-Frontend-1.0}"; Flags: nowait postinstall skipifsilent
Filename: "{app}\Integration-Project-Frontend-1.0.exe"; Parameters: "-install -svcName ""Integration-Project-Frontend-1.0"" -svcDesc ""Integration-Project-Frontend-1.0"" -mainExe ""Integration-Project-Frontend-1.0.exe""  "

[UninstallRun]
Filename: "{app}\Integration-Project-Frontend-1.0.exe"; Parameters: "-uninstall"; Flags: runhidden

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
  Result := True;
end;
