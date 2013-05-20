package management;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.elastictranscoder.*;
import com.amazonaws.services.elastictranscoder.model.*;

public class CopyOfTranscoder {

	private static AmazonElasticTranscoder transCoder;
	private static AWSCredentials credentials;
	private static String pipelineId = "1369004460848-trsdvb";

	private static void init() throws Exception {
		credentials = new ClasspathPropertiesFileCredentialsProvider()
				.getCredentials();
		System.out.println(credentials.getAWSAccessKeyId() + "   "
				+ credentials.getAWSSecretKey());
		transCoder = new AmazonElasticTranscoderClient(credentials);
		transCoder.setEndpoint("elastictranscoder.us-east-1.amazonaws.com");
	}

	public static void main(String[] args) throws Exception {
		init();

		// getOrCreatePipe();
		// CreatePipe();
		// deletePipe(pipelineId);
		// listPipe();

		//getPipe();
		 CreatJob();
		//ListJob();
	}

	public static void CancelJ(String id) {
		CancelJobRequest cancelJobRequest = new CancelJobRequest();
		cancelJobRequest.setId(id);
		CancelJobResult cancelJob = transCoder.cancelJob(cancelJobRequest);
	}

	public static Pipeline getOrCreatePipe() {
		Pipeline pipeline = new Pipeline();
		if (getPipe() != null) {
			pipeline = getPipe();
			System.out.println("Get a pipeline");
		} else {
			pipeline = CreatePipe();
			System.out.println("Create a new pipeline");
		}
		System.out.println(pipeline.getId());
		return pipeline;
	}

	public static void DeletePipe(String id) {
		DeletePipelineRequest deletePipelineRequest = new DeletePipelineRequest();
		deletePipelineRequest.setId(id);
		DeletePipelineResult deleResult = transCoder
				.deletePipeline(deletePipelineRequest);
	}

	public static Pipeline CreatePipe() {
		CreatePipelineRequest createPipelineRequest = new CreatePipelineRequest();
		createPipelineRequest.setName("ningxia91");
		createPipelineRequest.setInputBucket("ningxia91-bucket");
		createPipelineRequest.setOutputBucket("ningxia91-bucket");
		Notifications notifications = new Notifications();

		notifications
				.setWarning("arn:aws:sns:ap-northeast-1:919702891845:topic-sample");
		notifications
				.setProgressing("arn:aws:sns:ap-northeast-1:919702891845:topic-sample");
		notifications
				.setError("arn:aws:sns:ap-northeast-1:919702891845:topic-sample");
		notifications
				.setCompleted("arn:aws:sns:ap-northeast-1:919702891845:topic-sample");
		createPipelineRequest.setNotifications(notifications);
		createPipelineRequest
				.setRole("arn:aws:iam::919702891845:role/Elastic_Transcoder_Default_Role");

		CreatePipelineResult createPipelineResult = transCoder
				.createPipeline(createPipelineRequest);
		Pipeline p = createPipelineResult.getPipeline();
		System.out.println("----------New Pipeline----------");
		System.out.println("Id : " + p.getId());
		System.out.println("  Name : " + p.getName());
		System.out.println("  Status : " + p.getStatus());

		return p;
	}

	public static void deletePipe(String id) {
		DeletePipelineRequest deletePipelineRequest = new DeletePipelineRequest();
		deletePipelineRequest.setId(id);
		DeletePipelineResult deleResult = transCoder
				.deletePipeline(deletePipelineRequest);
	}

	public static void listPipe() {
		ListPipelinesRequest listPipelineRequest = new ListPipelinesRequest();
		// listPipelineRequest.setRequestCredentials(credentials);
		// System.out.println(listPipelineRequest.getRequestCredentials().getAWSAccessKeyId());
		ListPipelinesResult listPipelineResult = transCoder
				.listPipelines(listPipelineRequest);
		List<Pipeline> pipelines = listPipelineResult.getPipelines();

		System.out.println(pipelines.size());

		System.out.println("----------Pipeline List----------");
		for (Pipeline pipeline : pipelines) {
			System.out.println("Id : " + pipeline.getId());
			System.out.println("  Name : " + pipeline.getName());
			System.out.println("  Role : " + pipeline.getRole());
		}
	}

	public static Pipeline getPipe() {
		Pipeline p = null;
		ListPipelinesRequest listPipelineRequest = new ListPipelinesRequest();
		// listPipelineRequest.setRequestCredentials(credentials);
		// System.out.println(listPipelineRequest.getRequestCredentials().getAWSAccessKeyId());
		ListPipelinesResult listPipelineResult = transCoder
				.listPipelines(listPipelineRequest);
		List<Pipeline> pipelines = listPipelineResult.getPipelines();

		for (Pipeline pipeline : pipelines) {
			if (pipeline.getId().equals(pipelineId)) {
				p = pipelines.get(0);
				System.out.println("Id : " + p.getId());
				System.out.println("Name : " + p.getName());
				System.out.println("Role : " + p.getRole());
			}
		}
		return p;
	}

	public static void CreatJob() {
		CreateJobRequest createJobRequest = new CreateJobRequest();
		JobInput input = new JobInput();
		input.setKey("xiaoxin_3.mp4");
		input.setAspectRatio("auto");
		input.setContainer("auto");
		input.setFrameRate("auto");
		input.setInterlaced("auto");
		input.setResolution("auto");

		CreateJobOutput output = new CreateJobOutput();
		output.setRotate("auto");
		output.setKey("xiaoxin_3");
		output.setPresetId("1351620000001-000060");
		output.setSegmentDuration(null);
		output.setThumbnailPattern("-{count}");

		createJobRequest.setPipelineId(pipelineId);
		createJobRequest.setRequestCredentials(credentials);
		createJobRequest.setInput(input);
		createJobRequest.setOutput(output);
		createJobRequest.setOutputKeyPrefix("out_");

		try {
			CreateJobResult createJobResult = transCoder
					.createJob(createJobRequest);
			Job j = createJobResult.getJob();

			System.out.println("----------New Job----------");
			System.out.println("Id : " + j.getId());
		} catch (AmazonServiceException e) {
			System.err.println(e);
		}
	}

	public static void ListJob() {
		ListJobsByPipelineRequest listJobsByPipelineRequest = new ListJobsByPipelineRequest();
		listJobsByPipelineRequest.setPipelineId(pipelineId);
		ListJobsByPipelineResult listJobsByPipelineResult = transCoder
				.listJobsByPipeline(listJobsByPipelineRequest);
		List<Job> jobs = listJobsByPipelineResult.getJobs();
		for (Job job : jobs) {
			System.out.println("  Id : " + job.getId());
			JobInput jobInput = job.getInput();
			System.out.println("    JobInput");
			System.out.println("      AspectRatio : "
					+ jobInput.getAspectRatio());
			System.out.println("      Container : " + jobInput.getContainer());
			System.out.println("      FrameRate : " + jobInput.getFrameRate());
			System.out
					.println("      Interlaced : " + jobInput.getInterlaced());
			System.out.println("      Key : " + jobInput.getKey());
			System.out
					.println("      Resolution : " + jobInput.getResolution());
			JobOutput jobOutput = job.getOutput();
			System.out.println("    JobOutput");
			System.out.println("      Key : " + jobOutput.getKey());
			System.out.println("      PresetId : " + jobOutput.getPresetId());
			System.out.println("      Rotate : " + jobOutput.getRotate());
			System.out.println("      Status : " + jobOutput.getStatus());
			System.out.println("      StatusDetail : "
					+ jobOutput.getStatusDetail());
			System.out.println("      ThumbnailPattern : "
					+ jobOutput.getThumbnailPattern());
		}
	}
}
