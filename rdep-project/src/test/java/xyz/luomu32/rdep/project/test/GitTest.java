package xyz.luomu32.rdep.project.test;

import org.assertj.core.api.Assertions;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.junit.Test;

import java.io.File;

public class GitTest {

    @Test
    public void testCommitLog() {

        String lastCommitId = "9fc98d8e25999575dd009680370d898d6e377129";

        try (Git git = Git.open(new File("/Users/luomu32/rdep workdir/spring-cloud-demo/"))) {
            ObjectId head = git.getRepository().findRef("head").getObjectId();
            ObjectId lastCommit = git.getRepository().resolve(lastCommitId);
            git.log().addRange(lastCommit, head).call().forEach(commit -> {
                System.out.println(commit.getCommitTime());
                System.out.println(commit.getShortMessage());

                System.out.println(commit.getFullMessage());
                System.out.println(commit.getAuthorIdent().getName());
                System.out.println(commit.getAuthorIdent().getEmailAddress());
                System.out.println(commit.getId().getName());
                System.out.println("=======");
            });
        } catch (Exception e) {
        }
//        try (Repository repository = new FileRepository("");
//             RevWalk walk = new RevWalk(repository)) {
//
//        } catch (Exception e) {
//
//        }
    }

    @Test
    public void testGetHeadCommitId() {
        try (Git git = Git.open(new File("/Users/luomu32/rdep workdir/spring-cloud-demo/"))) {
            System.out.println(git.getRepository().resolve("head").getName());
            git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call().forEach(r -> {
                System.err.println(r.getName());
            });
        } catch (Exception e) {

        }
    }

    //    @Test
    public void testCreateBranch() {
        try (Git git = Git.open(new File("/Users/luomu32/rdep workdir/spring-cloud-demo/"))) {
            git.branchCreate().setName("develop").call();
        } catch (Exception e) {
        }
    }

    @Test
    public void testCheckOutBranch() {
        try (Git git = Git.open(new File("/Users/luomu32/rdep workdir/spring-cloud-demo/"))) {
            Ref ref = git.checkout().setName("develop1").call();
            System.out.println(ref.getObjectId());
            System.err.println(ref.getName());
            Assertions.assertThat(git.getRepository().getBranch()).isEqualTo("develop");
        } catch (Exception e) {
        }
    }
}
