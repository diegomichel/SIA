import java.awt.*;
import com.unioncomm.sdk.bsp.UCBioBSPJNI;
import javax.swing.JOptionPane;

public class UCBioBSPJNI_Test
{
	private UCBioBSPJNI bsp;

	public UCBioBSPJNI_Test()
	{
		bsp = new UCBioBSPJNI();
		if (bsp.IsErrorOccured()) {
			System.out.println(bsp.GetErrorCode());
			return;
		}
	}

	public void dispose()
	{
		if (bsp != null)
		{
			bsp.dispose();
			bsp = null;
		}
	}

	protected void finalize() throws Throwable
	{
		try
		{
			dispose();
		}
		finally
		{
			super.finalize();
		}
	}

	public void PrintBSPVersion()
	{
		String bspVersion = "";
		bspVersion = bsp.GetVersion();
		System.out.println("BSP Version : v" + bspVersion);
		System.out.println("");
	}

	public void PrintInitInfo()
	{
		UCBioBSPJNI.INIT_INFO_0 initInfo0 = bsp.new INIT_INFO_0();
		bsp.GetInitInfo(initInfo0);
		System.out.println("InitInfo :");
		System.out.println(" MaxFingersForEnroll : " + initInfo0.MaxFingersForEnroll);
		System.out.println(" NecessaryEnrollNum : " + initInfo0.NecessaryEnrollNum);
		System.out.println(" SamplesPerFinger : " + initInfo0.SamplesPerFinger);
		System.out.println(" DefaultTimeout : " + initInfo0.DefaultTimeout);
		System.out.println(" SecurityLevelForEnroll : " + initInfo0.SecurityLevelForEnroll);
		System.out.println(" SecurityLevelForVerify : " + initInfo0.SecurityLevelForVerify);
		System.out.println(" SecurityLevelForIdentify : " + initInfo0.SecurityLevelForIdentify);
		System.out.println("");
	}

	public void PrintDeviceInfo()
	{
		UCBioBSPJNI.DEVICE_ENUM_INFO deviceEnumInfo = bsp.new DEVICE_ENUM_INFO();
		int ret = bsp.EnumerateDevice(deviceEnumInfo);
		int i, n = deviceEnumInfo.DeviceInfo.length;

		System.out.println("EnumerateDeviceID : Total " + n);
		for (i = 0; i < n; i++)
		{
			System.out.println(" DeviceInfo : ");
			System.out.println("  - DeviceID : \t\t" + deviceEnumInfo.DeviceInfo[i].DeviceID);
			System.out.println("  - NameID : \t\t" + deviceEnumInfo.DeviceInfo[i].NameID);
			System.out.println("  - Instance : \t\t" + deviceEnumInfo.DeviceInfo[i].Instance);
			System.out.println("  - Name : \t\t" + deviceEnumInfo.DeviceInfo[i].Name);
			System.out.println("  - Description : \t" + deviceEnumInfo.DeviceInfo[i].Description);
			System.out.println("  - Dll : \t\t" + deviceEnumInfo.DeviceInfo[i].Dll);
			System.out.println("  - Sys : \t\t" + deviceEnumInfo.DeviceInfo[i].Sys);
			System.out.println("  - Brightness : \t" + deviceEnumInfo.DeviceInfo[i].Brightness);
			System.out.println("  - Contrast : \t\t" + deviceEnumInfo.DeviceInfo[i].Contrast);
			System.out.println("  - Gain : \t\t" + deviceEnumInfo.DeviceInfo[i].Gain);
			System.out.println("");
		}
		System.out.println("");

		UCBioBSPJNI.DEVICE_INFO deviceInfo = bsp.new DEVICE_INFO();
		bsp.OpenDevice();
		bsp.GetDeviceInfo(deviceInfo);
		System.out.println("Current DeviceInfo :");
		System.out.println(" ImageWidth : " + deviceInfo.ImageWidth);
		System.out.println(" ImageHeight : " + deviceInfo.ImageHeight);
		System.out.println(" Brightness : " + deviceInfo.Brightness);
		System.out.println(" Contrast : " + deviceInfo.Contrast);
		System.out.println(" Gain : " + deviceInfo.Gain);
		System.out.println("");
		bsp.CloseDevice();
	}

	public void AdjustDevice()
	{
		System.out.println("Adjust Device Start........");

		bsp.OpenDevice();
		bsp.AdjustDevice();
		bsp.CloseDevice();

		System.out.println("Adjust Device Ended........");
		System.out.println("");
	}

	public void Test1()
	{
		System.out.println("Test1 Start..(Not AutoDetect Verify)");

		JOptionPane.showMessageDialog(null,"open device: "+bsp.OpenDevice());

		bsp.SetAutoDetect(0);

		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
		UCBioBSPJNI.FIR_PAYLOAD enrollPayload = bsp.new FIR_PAYLOAD();
		enrollPayload.SetText("Have good time.!!!");

		int ret = bsp.Enroll(null, hFIR, enrollPayload, -1, null, null);
		if (ret == 0)
		{
			UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
			inputFIR.SetFIRHandle(hFIR);

			Boolean bResult = new Boolean(false); // never use like as 'Boolean bResult = false'.
			UCBioBSPJNI.FIR_PAYLOAD payload = bsp.new FIR_PAYLOAD();

			bsp.SetAutoDetect(1);

			bsp.Verify(inputFIR, bResult, payload, -1, null, null);
			if (bResult)
				System.out.println("Verify OK! - Payload: " + payload.GetText());
			else
				System.out.println("Failed to Verify!");
		}
		hFIR.dispose();

		bsp.CloseDevice();

		System.out.println("Test1 Ended........");
		System.out.println("");
	}

	
	public void Test2()
	{
		System.out.println("Test2 Start....(Not AutoDetect Enroll)");

		bsp.OpenDevice();

		bsp.SetAutoDetect(0);

		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
		int ret = bsp.Enroll(null, hFIR, null, -1, null, null);
		if (ret == 0)
		{
			UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
			inputFIR.SetFIRHandle(hFIR);

			UCBioBSPJNI.FIR_HANDLE hNewFIR = bsp.new FIR_HANDLE();
			ret = bsp.Enroll(inputFIR, hNewFIR, null, -1, null, null);
			if (ret == 0)
			{
				UCBioBSPJNI.FIR fullFIR = bsp.new FIR();
				bsp.GetFIRFromHandle(hNewFIR, fullFIR);

				UCBioBSPJNI.FIR_TEXTENCODE textFIR = bsp.new FIR_TEXTENCODE();
				bsp.GetTextFIRFromHandle(hNewFIR, textFIR);
                                System.out.println(textFIR.TextFIR);

				Boolean bResult = new Boolean(false); // never use like as 'Boolean bResult = false'.

				bsp.SetAutoDetect(0);

				inputFIR.SetFIRHandle(hNewFIR);
				bsp.Verify(inputFIR, bResult, null, -1, null, null);
				if (bResult)
					System.out.println("Verify OK! (HANDLE)");
				else
					System.out.println("Failed to Verify!");

				inputFIR.SetFullFIR(fullFIR);
				bsp.Verify(inputFIR, bResult, null, -1, null, null);
				if (bResult)
					System.out.println("Verify OK! (FullFIR)");
				else
					System.out.println("Failed to Verify!");

				inputFIR.SetTextFIR(textFIR);
				bsp.Verify(inputFIR, bResult, null, -1, null, null);
				if (bResult)
					System.out.println("Verify OK! (TextFIR)");
				else
					System.out.println("Failed to Verify!");
			}

			hNewFIR.dispose();
		}
		hFIR.dispose();

		bsp.CloseDevice();

		System.out.println("Test2 Ended........");
		System.out.println("");
	}

	public void Test3()
	{
		System.out.println("Test3 Start........");

		bsp.OpenDevice();
                bsp.SetAutoDetect(1);

		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
		int ret = bsp.Capture(1, hFIR, -1, null, null);
		if (ret == 0)
		{
			Frame f;
			f = new Frame("Test");
			f.setBounds(0, 0, 248, 292);
			f.setVisible(true);

			Canvas c = new Canvas();
			c.setSize(248, 292);
			c.setBackground(Color.WHITE);
			f.add(c);

			UCBioBSPJNI.WINDOW_OPTION winOption = bsp.new WINDOW_OPTION();
			winOption.WindowStyle = 1;
			winOption.FingerWnd = c;
			String jreBinPath = System.getProperty("java.home") + "\\bin";
			winOption.JreBinPath = jreBinPath;

			UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
			inputFIR.SetFIRHandle(hFIR);

			Boolean bResult = new Boolean(false); // never use like as 'Boolean bResult = false'.
			
			bsp.Verify(inputFIR, bResult, null, -1, null, winOption);
			if (bResult)
				System.out.println("Verify OK!");
			else
				System.out.println("Failed to Verify!");

			f.setVisible(false);
			f.dispose();			
		}
		hFIR.dispose();

		bsp.CloseDevice();

		System.out.println("Test3 Ended........");
		System.out.println("");
	}

	public void TestVerifyMatch()
	{
		System.out.println("TestVerifyMatch Start........");

		UCBioBSPJNI.FIR_HANDLE hFIR1 = bsp.new FIR_HANDLE();
		UCBioBSPJNI.FIR_HANDLE hFIR2 = bsp.new FIR_HANDLE();

		int ret;

		bsp.OpenDevice();
		ret = bsp.Capture(hFIR1);
		if (ret == 0)
			ret = bsp.Capture(hFIR2);
		bsp.CloseDevice();

		if (ret == 0) {
			UCBioBSPJNI.INPUT_FIR inputFIR1 = bsp.new INPUT_FIR();
			inputFIR1.SetFIRHandle(hFIR1);

			UCBioBSPJNI.INPUT_FIR inputFIR2 = bsp.new INPUT_FIR();
			inputFIR2.SetFIRHandle(hFIR2);

			Boolean bResult = new Boolean(false); // never use like as 'Boolean bResult = false'.

			ret = bsp.VerifyMatch(inputFIR1, inputFIR2, bResult, null);
			if (ret == 0) {
				if (bResult)
					System.out.println("Verify OK!");
				else
					System.out.println("Failed to Verify!");
			}
		}

		System.out.println("TestVerifyMatch Ended........");
		System.out.println("");
	}

	public void TestFastSearch()
	{
		System.out.println("TestFastSearch Start........");

		UCBioBSPJNI.FastSearch isEngine = bsp.new FastSearch();

		UCBioBSPJNI.FastSearch.INIT_INFO isInitInfo = isEngine.new INIT_INFO();
		isEngine.GetInitInfo(isInitInfo);

		System.out.println("FastSearch InitInfo :");
		System.out.println(" UseGroupMatch : " + isInitInfo.UseGroupMatch);
      System.out.println(" MatchMethod : " + isInitInfo.MatchMethod);
		System.out.println("");

		bsp.OpenDevice();

		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
		int ret = bsp.Enroll(hFIR, null);
		if (ret == 0)
		{
			UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
			inputFIR.SetFIRHandle(hFIR);

			UCBioBSPJNI.FastSearch.SAMPLE_INFO sampleInfo = isEngine.new SAMPLE_INFO();
			isEngine.AddFIR(inputFIR, 1, sampleInfo);

			System.out.println("FastSearch Add FIR :");
			System.out.println(" SampleInfo ID : " + sampleInfo.ID);
			System.out.println(" SampleInfo SampleCount : ");
			int i, n = sampleInfo.SampleCount.length;
			for (i = 0; i < n; i++)
			{
				System.out.println("   " + i + ") " + sampleInfo.SampleCount[i]);
			}
			System.out.println("");
		}

		if (ret == 0)
		{
			Integer nCount = new Integer(0);	// never use like as 'Integer cCount = 0'.
			isEngine.GetDBCount(nCount);

			System.out.println(" FastSearch DB count : " + nCount);
			System.out.println("");

			UCBioBSPJNI.FIR_HANDLE hCapturedFIR = bsp.new FIR_HANDLE();
			ret = bsp.Capture(hCapturedFIR);
			if (ret == 0)
			{
				UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
				inputFIR.SetFIRHandle(hCapturedFIR);

				UCBioBSPJNI.FastSearch.FP_INFO fpInfo = isEngine.new FP_INFO();
				ret = isEngine.Identify(inputFIR, 5, fpInfo);
				if (ret == 0)
				{
					System.out.println("FastSearch Identify :");
					System.out.println(" Result ID : " + fpInfo.ID);
					System.out.println(" Result FingerID : " + fpInfo.FingerID);
					System.out.println(" Result SampleNumber : " + fpInfo.SampleNumber);
					System.out.println("");
				}

				Boolean bExist = new Boolean(false); // never use like as 'Boolean bExist = false'.
				ret = isEngine.CheckDataExist(fpInfo, bExist);
				if (ret == 0)
				{
					System.out.println("FastSearch CheckDataExist :");
					System.out.println(" Exist? : " + bExist);
					System.out.println("");
				}
			}
		}

		bsp.CloseDevice();

		System.out.println("TestFastSearch Ended........");
		System.out.println("");
	}

	public void TestExport()
	{
		System.out.println("TestExport Start........");

		UCBioBSPJNI.Export exportEngine = bsp.new Export();

		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();

		bsp.OpenDevice();
		int ret = bsp.Enroll(hFIR, null);
		bsp.CloseDevice();

		if (ret == 0)
		{
			UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
			inputFIR.SetFIRHandle(hFIR);

			UCBioBSPJNI.Export.DATA exportData = exportEngine.new DATA();

			ret = exportEngine.ExportFIR(inputFIR, exportData, UCBioBSPJNI.EXPORT_TEMPLATE_TYPE.SIZE400);
			if (ret == 0)
			{
				System.out.println(" Template Type            : " + exportData.TemplateType);
				System.out.println(" Total Exported Finger    : " + exportData.FingerNum);
				System.out.println(" Sample Number Per Finger : " + exportData.SamplesPerFinger);

				int i, n = exportData.FingerInfo.length, j, m, k, l;
				for (i = 0; i < n; i++)
				{
					System.out.println(" FingerID : " + exportData.FingerInfo[i].FingerID);

					m = exportData.FingerInfo[i].TemplateInfo.length;
					for (j = 0; j < m; j++)
					{
						System.out.println("   Sample Num : " + j);

						l = exportData.FingerInfo[i].TemplateInfo[j].Data.length;
						System.out.println("   Data Length : " + l);

						System.out.println("=(Data block)===========================");
						for (k = 0; k < l; k++)
						{
							System.out.print(exportData.FingerInfo[i].TemplateInfo[j].Data[k] + ",");
						}
						System.out.println("");
						System.out.println("=(Data block)===========================");
						System.out.println("");
					}
				}
			}

			// Import Test
			if (ret == 0)
			{
				UCBioBSPJNI.FIR_HANDLE hNewFIR = bsp.new FIR_HANDLE();
				ret = exportEngine.ImportFIR(exportData, hNewFIR);

				if (ret == 0)
				{
					inputFIR.SetFIRHandle(hNewFIR);

					Boolean bResult = new Boolean(false); // never use like as 'Boolean bResult = false'.

					bsp.OpenDevice();
					bsp.Verify(inputFIR, bResult, null, -1, null, null);
					bsp.CloseDevice();

					if (bResult)
						System.out.println("Verify OK! (HANDLE)");
					else
						System.out.println("Failed to Verify!");
				}
			}
		}
		
		System.out.println("TestExport Ended........");
		System.out.println("");
	}

	public void TestExportAudit()
	{
		System.out.println("TestExportAudit Start........");

		UCBioBSPJNI.Export exportEngine = bsp.new Export();

		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
		UCBioBSPJNI.FIR_HANDLE hAuditFIR = bsp.new FIR_HANDLE();

		bsp.OpenDevice();
		int ret = bsp.Enroll(null, hFIR, null, -1, hAuditFIR, null);
		bsp.CloseDevice();

		if (ret == 0)
		{
			UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
			inputFIR.SetFIRHandle(hAuditFIR);

			UCBioBSPJNI.Export.AUDIT exportAudit = exportEngine.new AUDIT();

			ret = exportEngine.ExportAudit(inputFIR, exportAudit);
			if (ret == 0)
			{
				System.out.println(" Total Exported Finger    : " + exportAudit.FingerNum);
				System.out.println(" Sample Number Per Finger : " + exportAudit.SamplesPerFinger);

				int i, n = exportAudit.FingerInfo.length, j, m, k, l;
				for (i = 0; i < n; i++)
				{
					System.out.println(" FingerID : " + exportAudit.FingerInfo[i].FingerID);

					m = exportAudit.FingerInfo[i].TemplateInfo.length;
					for (j = 0; j < m; j++)
					{
						System.out.println("   Sample Num : " + j);

						l = exportAudit.FingerInfo[i].TemplateInfo[j].Data.length;
						System.out.println("   Data Length : " + l + " (" + (l / 1024) + " kbyte)");

						System.out.println("=(Data block)===========================");
						for (k = 0; k < 1024; k++)
						{
							System.out.print(exportAudit.FingerInfo[i].TemplateInfo[j].Data[k] + ",");
						}
						System.out.println("");
						System.out.println("...(ellipsis)...");
						System.out.println("=(Data block)===========================");
						System.out.println("");
					}
				}
			}

			// Import Test
			if (ret == 0)
			{
				UCBioBSPJNI.FIR_HANDLE hNewFIR = bsp.new FIR_HANDLE();
				ret = exportEngine.ImportAudit(exportAudit, hNewFIR);

				if (ret == 0)
				{
					inputFIR.SetFIRHandle(hNewFIR);

					Boolean bResult = new Boolean(false); // never use like as 'Boolean bResult = false'.

					bsp.OpenDevice();
					bsp.Verify(inputFIR, bResult, null, -1, null, null);
					bsp.CloseDevice();

					if (bResult)
						System.out.println("Verify OK! (HANDLE)");
					else
						System.out.println("Failed to Verify!");
				}
			}
		}

		System.out.println("TestExportAudit Ended........");
		System.out.println("");
	}

	public void TestFastSearchKKS1()
	{
		System.out.println("TestFastSearch Start(KKS)........");

		UCBioBSPJNI.FastSearch isEngine = bsp.new FastSearch();

		UCBioBSPJNI.FastSearch.INIT_INFO isInitInfo = isEngine.new INIT_INFO();
		isEngine.GetInitInfo(isInitInfo);

		System.out.println("FastSearch InitInfo :");
		System.out.println(" UseGroupMatch : " + isInitInfo.UseGroupMatch);
		System.out.println(" MatchMethod : " + isInitInfo.MatchMethod);
		System.out.println("");

		bsp.OpenDevice();

		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
		int ret = bsp.Enroll(hFIR, null);
		if (ret == 0)
		{
			UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
			inputFIR.SetFIRHandle(hFIR);

			UCBioBSPJNI.FastSearch.SAMPLE_INFO sampleInfo = isEngine.new SAMPLE_INFO();
			isEngine.AddFIR(inputFIR, 777, sampleInfo);

			System.out.println("FastSearch Add FIR :" + "777");
			System.out.println(" SampleInfo ID : " + sampleInfo.ID);
			System.out.println(" SampleInfo SampleCount : ");
			int i, n = sampleInfo.SampleCount.length;
			System.out.println("");
		}

		if (ret == 0)
		{
			Integer nCount = new Integer(0);	// never use like as 'Integer cCount = 0'.
			isEngine.GetDBCount(nCount);

			System.out.println(" FastSearch DB count : " + nCount);
			System.out.println("");

			UCBioBSPJNI.FIR_HANDLE hCapturedFIR = bsp.new FIR_HANDLE();
			ret = bsp.Capture(hCapturedFIR);
			if (ret == 0)
			{
				UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();

				inputFIR.SetFIRHandle(hCapturedFIR);

				UCBioBSPJNI.FastSearch.FP_INFO fpInfo = isEngine.new FP_INFO();
				ret = isEngine.Identify(inputFIR, 5, fpInfo);
				if (ret == 0)
				{
					System.out.println("FastSearch Identify : OK");
					System.out.println(" Result ID : " + fpInfo.ID);
					System.out.println(" Result FingerID : " + fpInfo.FingerID);
					System.out.println(" Result SampleNumber : " + fpInfo.SampleNumber);
					System.out.println("");
				}
				else
				{
					System.out.println("FastSearch Identify : FAIL.!!!!!! [Ret : " + ret + "]");
				}
			}
		}

		bsp.CloseDevice();

		System.out.println("TestFastSearch Ended........");
		System.out.println("");
	}

	public void TestFastSearchKKS2()
	{
		System.out.println("TestFastSearch Start(KKS)........");

		UCBioBSPJNI.FastSearch isEngine = bsp.new FastSearch();

		UCBioBSPJNI.FastSearch.INIT_INFO isInitInfo = isEngine.new INIT_INFO();
		isEngine.GetInitInfo(isInitInfo);

		System.out.println("FastSearch InitInfo :");
		System.out.println(" UseGroupMatch : " + isInitInfo.UseGroupMatch);
		System.out.println(" MatchMethod : " + isInitInfo.MatchMethod);
		System.out.println("");

		bsp.OpenDevice();

		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
		int ret = bsp.Enroll(hFIR, null);
		if (ret == 0)
		{
			UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
			inputFIR.SetFIRHandle(hFIR);

			UCBioBSPJNI.FastSearch.SAMPLE_INFO sampleInfo = isEngine.new SAMPLE_INFO();
			isEngine.AddFIR(inputFIR, 777, sampleInfo);

			System.out.println("FastSearch Add FIR :" + "777");
			System.out.println(" SampleInfo ID : " + sampleInfo.ID);
			System.out.println(" SampleInfo SampleCount : ");
			int i, n = sampleInfo.SampleCount.length;
			System.out.println("");
		}

		if (ret == 0)
		{
			Integer nCount = new Integer(0);	// never use like as 'Integer cCount = 0'.
			isEngine.GetDBCount(nCount);

			System.out.println(" FastSearch DB count : " + nCount);
			System.out.println("");

			UCBioBSPJNI.FIR_HANDLE hCapturedFIR = bsp.new FIR_HANDLE();
			ret = bsp.Capture(hCapturedFIR);
			if (ret == 0)
			{
				// Ư¡�� ����
				UCBioBSPJNI.Export exportEngine = bsp.new Export();

				byte fpdata[];
				fpdata = new byte[8000];

				int fplen = 0;

				UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
				inputFIR.SetFIRHandle(hCapturedFIR);
				UCBioBSPJNI.Export.DATA exportData = exportEngine.new DATA();
				ret = exportEngine.ExportFIR(inputFIR, exportData, UCBioBSPJNI.EXPORT_TEMPLATE_TYPE.SIZE400);
				if (ret == 0)
				{
					System.out.println(" Template Type            : " + exportData.TemplateType);
					System.out.println(" Total Exported Finger    : " + exportData.FingerNum);
					System.out.println(" Sample Number Per Finger : " + exportData.SamplesPerFinger);
					int i, n = exportData.FingerInfo.length, j, m, k, l;
					for (i = 0; i < n; i++)
					{
						System.out.println(" FingerID : " + exportData.FingerInfo[i].FingerID);
						m = exportData.FingerInfo[i].TemplateInfo.length;
						for (j = 0; j < m; j++)
						{
							System.out.println("   Sample Num : " + j);

							l = exportData.FingerInfo[i].TemplateInfo[j].Data.length;
							System.out.println("   Data Length : " + l);
						}
					}
					fplen = exportData.FingerInfo[0].TemplateInfo[0].Data.length;

					for(i=0;i<fplen;i++)
					{
						fpdata[i] = exportData.FingerInfo[0].TemplateInfo[0].Data[i];
					}
				}
				else
				{
					System.out.println("Export FAIL.!!!!!! [Ret : " + ret + "]");
					return;
				}	

				// Import Test
				if (ret == 0)
				{
					System.out.println("Import start.");

					// Ư¡������ Export Data ������ 
					UCBioBSPJNI.Export.DATA exdata = exportEngine.new DATA();
					exdata.TemplateType = UCBioBSPJNI.EXPORT_TEMPLATE_TYPE.SIZE400;
					exdata.FingerNum = 1;
					exdata.DefaultFingerID = 1;
					exdata.SamplesPerFinger= 1;
					exdata.FingerInfo = new UCBioBSPJNI.Export.FINGER_BLOCK[1];
					exdata.FingerInfo[0] = exportEngine.new FINGER_BLOCK();
					exdata.FingerInfo[0].FingerID = 1;
					exdata.FingerInfo[0].TemplateInfo = new UCBioBSPJNI.Export.TEMPLATE_BLOCK[1];
					exdata.FingerInfo[0].TemplateInfo[0] = exportEngine.new TEMPLATE_BLOCK();
					exdata.FingerInfo[0].TemplateInfo[0].Data = new byte[fplen];

					System.out.println("fpLength :" + fplen);
					System.out.println("TemplateLen : " + exdata.FingerInfo[0].TemplateInfo[0].Data.length);


					int i;
					for(i = 0; i < fplen; i++)
					{
						exdata.FingerInfo[0].TemplateInfo[0].Data[i] = fpdata[i];
					}

					UCBioBSPJNI.FIR_HANDLE hNewFIR = bsp.new FIR_HANDLE();
					ret = exportEngine.ImportFIR(exdata, hNewFIR);


					if (ret == 0)
					{
						UCBioBSPJNI.INPUT_FIR inFIR = bsp.new INPUT_FIR();
						inFIR.SetFIRHandle(hNewFIR);

						System.out.println("FastSearch Identify Start.!!!");

						UCBioBSPJNI.FastSearch.FP_INFO fpInfo = isEngine.new FP_INFO();
						ret = isEngine.Identify(inFIR, 5, fpInfo);
						if (ret == 0)
						{
							System.out.println("FastSearch Identify : OK");
							System.out.println(" Result ID : " + fpInfo.ID);
							System.out.println(" Result FingerID : " + fpInfo.FingerID);
							System.out.println(" Result SampleNumber : " + fpInfo.SampleNumber);
							System.out.println("");
						}
						else
						{
							System.out.println("FastSearch Identify : FAIL.!!!!!! [Ret : " + ret + "]");
						}
					}
					else
					{
						System.out.println("Import Error.!!!!");
					}
				}
			}
		}

		bsp.CloseDevice();

		System.out.println("TestFastSearch Ended........");
		System.out.println("");
	}

	public void TestFastSearchKKS3()
	{
		System.out.println("TestFastSearch Start(KKS)........");

		UCBioBSPJNI.FastSearch isEngine = bsp.new FastSearch();

		UCBioBSPJNI.FastSearch.INIT_INFO isInitInfo = isEngine.new INIT_INFO();
		isEngine.GetInitInfo(isInitInfo);

		System.out.println("FastSearch InitInfo :");
		System.out.println(" UseGroupMatch : " + isInitInfo.UseGroupMatch);
		System.out.println(" MatchMethod : " + isInitInfo.MatchMethod);
		System.out.println("");

		bsp.OpenDevice();

		UCBioBSPJNI.Export exportEngine = bsp.new Export();

		// Ư¡������ Export Data ������ 
		char[] arr = "554E494F4E055A0003389F0017484B319FA6197580ADD85153289715992C5944469B296D5CBCA8430BEB40D090C9F796FB0DEBCA1E2E58DF2DEAC5CB39894845F4BAC0AE4B2275FC79F7803C61181E98C60B862B0D24CC80910735C3240BA9C84EBD06C4E11F7DDB499C3EC2A760DAC7F81C958B356412AC18902B53DCBB1750FC449980A07A088A9C46E5AE49BA43F080E2DC5AD934A29836C0B37470CCD7EE10EF914240DBF4A8669DB2B0E1173B2D100F3A3B76A024E1A8E6F0D4F4FA08969FFE644A59AB113F3CC10922D729D24484028AD50A7700DACE231CE0BFE0F29814DDA3912D1255BACB34880577BB1DC74D74D8613CB1BE1A70D5BC9B8498E9347D409C1BC21FE37C2B5304AD71B787AF5A37C1D20988D3A9642E8EC78DEBA41864B3D69C273B7D90DEDF1A8C1BA1D1CC7DE148671BEF3EE8C7D701CAA45531D414C572F35D4E7E5D663904BE9A3C8A7D5B8D4A431341B8AD5C2D45308D2BA5F683B557EA44CE418533BDC4A805C4C26EFD643A7A6A9A413696713DBDA429F860F4604AF0D7FBABFCB1E564E8A4C8C02C".toCharArray();
		UCBioBSPJNI.Export.DATA exdata = exportEngine.new DATA();
		exdata.TemplateType = UCBioBSPJNI.EXPORT_TEMPLATE_TYPE.SIZE400;
		exdata.FingerNum = 1;
		exdata.DefaultFingerID = 1;
		exdata.SamplesPerFinger= 1;
		exdata.FingerInfo = new UCBioBSPJNI.Export.FINGER_BLOCK[1];
		exdata.FingerInfo[0] = exportEngine.new FINGER_BLOCK();
		exdata.FingerInfo[0].FingerID = 1;
		exdata.FingerInfo[0].TemplateInfo = new UCBioBSPJNI.Export.TEMPLATE_BLOCK[1];
		exdata.FingerInfo[0].TemplateInfo[0] = exportEngine.new TEMPLATE_BLOCK();
		exdata.FingerInfo[0].TemplateInfo[0].Data = new byte[400];
		int j=0;
        for(int i=0; i<arr.length; i=i+2)
		{
			String str = "" + arr[i] + arr[i+1];
			char ch = (char)Integer.parseInt(str, 16);
			String asciiChar = ""+ch;
			byte[] b = asciiChar.getBytes();
			for(int k=0; k<b.length; k++)
			{
				exdata.FingerInfo[0].TemplateInfo[0].Data[j] = b[k];
				System.out.print(exdata.FingerInfo[0].TemplateInfo[0].Data[j] + ",");
			}
			j++;
        }
		UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
		int ret = exportEngine.ImportFIR(exdata, hFIR);
		if (ret == 0)
		{
			inputFIR.SetFIRHandle(hFIR);

// Export Data �غ��� ���Ŷ� �Ȱ����� ??????????
			UCBioBSPJNI.Export.DATA exportData = exportEngine.new DATA();
			ret = exportEngine.ExportFIR(inputFIR, exportData, UCBioBSPJNI.EXPORT_TEMPLATE_TYPE.SIZE400);
			if (ret == 0)
			{
				System.out.println(" Template Type            : " + exportData.TemplateType);
				System.out.println(" Total Exported Finger    : " + exportData.FingerNum);
				System.out.println(" Sample Number Per Finger : " + exportData.SamplesPerFinger);

				int i, n = exportData.FingerInfo.length, m, k, l;
				for (i = 0; i < n; i++)
				{
					System.out.println(" FingerID : " + exportData.FingerInfo[i].FingerID);

					m = exportData.FingerInfo[i].TemplateInfo.length;
					for (j = 0; j < m; j++)
					{
						System.out.println("   Sample Num : " + j);

						l = exportData.FingerInfo[i].TemplateInfo[j].Data.length;
						System.out.println("   Data Length : " + l);

						System.out.println("=(Data block)===========================");
						for (k = 0; k < l; k++)
						{
							System.out.print(exportData.FingerInfo[i].TemplateInfo[j].Data[k] + ",");
						}
						System.out.println("");
						System.out.println("=(Data block)===========================");
						System.out.println("");
					}
				}
			}
			else
			{
				System.out.println("#### Export Error [Err :" + ret);
			}
///////////////////////////////////////////////////

			UCBioBSPJNI.FastSearch.SAMPLE_INFO sampleInfo = isEngine.new SAMPLE_INFO();
			isEngine.AddFIR(inputFIR, 777, sampleInfo);




			System.out.println("FastSearch Add FIR :" + "777");
			System.out.println(" SampleInfo ID : " + sampleInfo.ID);
		}

		if (ret == 0)
		{
			Integer nCount = new Integer(0);	// never use like as 'Integer cCount = 0'.
			isEngine.GetDBCount(nCount);

			System.out.println(" FastSearch DB count : " + nCount);
			System.out.println("");

			// Ư¡������ Export Data ������ 
/*
			UCBioBSPJNI.Export.DATA exdata = exportEngine.new DATA();
			exdata.TemplateType = UCBioBSPJNI.EXPORT_TEMPLATE_TYPE.SIZE400;
			exdata.FingerNum = 1;
			exdata.DefaultFingerID = 1;
			exdata.SamplesPerFinger= 1;
			exdata.FingerInfo = new UCBioBSPJNI.Export.FINGER_BLOCK[1];
			exdata.FingerInfo[0] = exportEngine.new FINGER_BLOCK();
			exdata.FingerInfo[0].FingerID = 1;
			exdata.FingerInfo[0].TemplateInfo = new UCBioBSPJNI.Export.TEMPLATE_BLOCK[1];
			exdata.FingerInfo[0].TemplateInfo[0] = exportEngine.new TEMPLATE_BLOCK();
			exdata.FingerInfo[0].TemplateInfo[0].Data = new byte[fplen];

			int i;
			for(i = 0; i < fplen; i++)
			{
				exdata.FingerInfo[0].TemplateInfo[0].Data[i] = fpdata[i];
			}

*/
			UCBioBSPJNI.FIR_HANDLE hNewFIR = bsp.new FIR_HANDLE();
			ret = exportEngine.ImportFIR(exdata, hNewFIR);
			if (ret == 0)
			{
				UCBioBSPJNI.INPUT_FIR inFIR = bsp.new INPUT_FIR();
				inFIR.SetFIRHandle(hNewFIR);

				System.out.println("FastSearch Identify Start.!!!");

				UCBioBSPJNI.FastSearch.FP_INFO fpInfo = isEngine.new FP_INFO();
//				ret = isEngine.Identify(inFIR, 5, fpInfo);
				ret = isEngine.Identify(inputFIR, 5, fpInfo);
				if (ret == 0)
				{
					System.out.println("FastSearch Identify : OK");
					System.out.println(" Result ID : " + fpInfo.ID);
					System.out.println(" Result FingerID : " + fpInfo.FingerID);
					System.out.println(" Result SampleNumber : " + fpInfo.SampleNumber);
					System.out.println("");
				}
				else
				{
					System.out.println("FastSearch Identify : FAIL.!!!!!! [Ret : " + ret + "]");
				}
			}
			else
			{
				System.out.println("Import Error.!!!!");
			}
		}

		bsp.CloseDevice();

		System.out.println("TestFastSearch Ended........");
		System.out.println("");
	}

	public void TestFastSearchKKS4()
	{
		System.out.println("TestFastSearch Start(KKS)........");

		UCBioBSPJNI.FastSearch isEngine = bsp.new FastSearch();

		UCBioBSPJNI.FastSearch.INIT_INFO isInitInfo = isEngine.new INIT_INFO();
		isEngine.GetInitInfo(isInitInfo);

		System.out.println("FastSearch InitInfo :");
		System.out.println(" UseGroupMatch : " + isInitInfo.UseGroupMatch);
		System.out.println(" MatchMethod : " + isInitInfo.MatchMethod);
		System.out.println("");

		bsp.OpenDevice();

		UCBioBSPJNI.FIR_HANDLE hFIR = bsp.new FIR_HANDLE();
		UCBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
		int ret = bsp.Capture(hFIR);
		if (ret == 0)
		{
			inputFIR.SetFIRHandle(hFIR);

			UCBioBSPJNI.FastSearch.SAMPLE_INFO sampleInfo = isEngine.new SAMPLE_INFO();
			isEngine.AddFIR(inputFIR, 777, sampleInfo);

			System.out.println("FastSearch Add FIR :" + "777");
			System.out.println(" SampleInfo ID : " + sampleInfo.ID);
			System.out.println(" SampleInfo SampleCount : ");
			int i, n = sampleInfo.SampleCount.length;
			System.out.println("");
		}

		if (ret == 0)
		{
			Integer nCount = new Integer(0);	// never use like as 'Integer cCount = 0'.
			isEngine.GetDBCount(nCount);

			System.out.println(" FastSearch DB count : " + nCount);
			System.out.println("");

			UCBioBSPJNI.FastSearch.FP_INFO fpInfo = isEngine.new FP_INFO();
			ret = isEngine.Identify(inputFIR, 5, fpInfo);
			if (ret == 0)
			{
				System.out.println("FastSearch Identify : OK");
				System.out.println(" Result ID : " + fpInfo.ID);
				System.out.println(" Result FingerID : " + fpInfo.FingerID);
				System.out.println(" Result SampleNumber : " + fpInfo.SampleNumber);
				System.out.println("");
			}
			else
			{
				System.out.println("FastSearch Identify : FAIL.!!!!!! [Ret : " + ret + "]");
			}
		}

		bsp.CloseDevice();

		System.out.println("TestFastSearch Ended........");
		System.out.println("");
	}

	public static void main(String args[])
	{
		UCBioBSPJNI_Test test = new UCBioBSPJNI_Test();

		test.PrintBSPVersion();
		test.PrintInitInfo();
//		test.PrintDeviceInfo();
//		test.AdjustDevice();

//		test.TestFastSearchKKS1();
//		test.TestFastSearchKKS2();
//		test.TestFastSearchKKS3();
//		test.TestFastSearchKKS4();


		test.Test1();
//		test.Test2();
//		test.Test3();
//		test.TestVerifyMatch();
//		test.TestFastSearch();
//		test.TestExport();
//		test.TestExportAudit();
		
		test.dispose();

		System.gc();
		System.runFinalization();
		System.gc();
	}
}