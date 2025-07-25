package org.jabref.logic.importer.fetcher;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;

import org.jabref.logic.importer.FetcherException;
import org.jabref.logic.importer.ImporterPreferences;
import org.jabref.logic.importer.PagedSearchBasedFetcher;
import org.jabref.logic.importer.SearchBasedFetcher;
import org.jabref.logic.util.BuildInfo;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.StandardField;
import org.jabref.model.entry.types.StandardEntryType;
import org.jabref.testutils.category.FetcherTest;

import com.airhacks.afterburner.injection.Injector;
import kong.unirest.core.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@FetcherTest
class SpringerFetcherTest implements SearchBasedFetcherCapabilityTest, PagedSearchFetcherTest {

    ImporterPreferences importerPreferences = mock(ImporterPreferences.class);
    SpringerFetcher fetcher;

    @BeforeEach
    void setUp() {
        BuildInfo buildInfo = Injector.instantiateModelOrService(BuildInfo.class);
        fetcher = new SpringerFetcher(importerPreferences);
        when(importerPreferences.getApiKeys()).thenReturn(FXCollections.emptyObservableSet());
        when(importerPreferences.getApiKey(fetcher.getName())).thenReturn(Optional.of(buildInfo.springerNatureAPIKey));
    }

    @Test
    void searchByQueryFindsEntry() throws FetcherException {

        BibEntry articleSupportingIdentification = new BibEntry(StandardEntryType.Article)
                .withField(StandardField.AUTHOR, "Iftikhar, Umar and Börstler, Jürgen and Bin Ali, Nauman and Kopp, Oliver")
                .withField(StandardField.DATE, "2025-04-23")
                .withField(StandardField.DOI, "10.1007/s11219-025-09720-9")
                .withField(StandardField.FILE, ":http\\://link.springer.com/openurl/pdf?id=doi\\:10.1007/s11219-025-09720-9:PDF")
                .withField(StandardField.ISSN, "0963-9314")
                .withField(StandardField.JOURNAL, "Software Quality Journal")
                .withField(StandardField.MONTH, "#apr#")
                .withField(StandardField.NUMBER, "2")
                .withField(StandardField.PAGES, "1--34")
                .withField(StandardField.PUBLISHER, "Springer")
                .withField(StandardField.TITLE, "Supporting the identification of prevalent quality issues in code changes by analyzing reviewers’ feedback")
                .withField(StandardField.VOLUME, "33")
                .withField(StandardField.YEAR, "2025")
                .withField(StandardField.ABSTRACT, "Context: Code reviewers provide valuable feedback during the code review. Identifying common issues described in the reviewers’ feedback can provide input for devising context-specific software development improvements. However, the use of reviewer feedback for this purpose is currently less explored. Objective: In this study, we assess how automation can derive more interpretable and informative themes in reviewers’ feedback and whether these themes help to identify recurring quality-related issues in code changes. Method: We conducted a participatory case study using the JabRef system to analyze reviewers’ feedback on merged and abandoned code changes. We used two promising topic modeling methods (GSDMM and BERTopic) to identify themes in 5,560 code review comments. The resulting themes were analyzed and named by a domain expert from JabRef. Results: The domain expert considered the identified themes from the two topic models to represent quality-related issues. Different quality issues are pointed out in code reviews for merged and abandoned code changes. While BERTopic provides higher objective coherence, the domain expert considered themes from short-text topic modeling more informative and easy to interpret than BERTopic-based topic modeling. Conclusions: The identified prevalent code quality issues aim to address the maintainability-focused issues. The analysis of code review comments can enhance the current practices for JabRef by improving the guidelines for new developers and focusing discussions in the developer forums. The topic model choice impacts the interpretability of the generated themes, and a higher coherence (based on objective measures) of generated topics did not lead to improved interpretability by a domain expert.");

        BibEntry articleTagThatIssue = new BibEntry(StandardEntryType.Article)
                .withField(StandardField.AUTHOR, "Santos, Fabio and Vargovich, Joseph and Trinkenreich, Bianca and Santos, Italo and Penney, Jacob and Britto, Ricardo and Pimentel, João Felipe and Wiese, Igor and Steinmacher, Igor and Sarma, Anita and Gerosa, Marco A.")
                .withField(StandardField.DATE, "2023-08-31")
                .withField(StandardField.DOI, "10.1007/s10664-023-10329-4")
                .withField(StandardField.FILE, ":http\\://link.springer.com/openurl/pdf?id=doi\\:10.1007/s10664-023-10329-4:PDF")
                .withField(StandardField.ISSN, "1382-3256")
                .withField(StandardField.JOURNAL, "Empirical Software Engineering")
                .withField(StandardField.MONTH, "#aug#")
                .withField(StandardField.NUMBER, "5")
                .withField(StandardField.PAGES, "1--52")
                .withField(StandardField.PUBLISHER, "Springer")
                .withField(StandardField.TITLE, "Tag that issue: applying API-domain labels in issue tracking systems")
                .withField(StandardField.VOLUME, "28")
                .withField(StandardField.YEAR, "2023")
                .withField(StandardField.ABSTRACT, "Labeling issues with the skills required to complete them can help contributors to choose tasks in Open Source Software projects. However, manually labeling issues is time-consuming and error-prone, and current automated approaches are mostly limited to classifying issues as bugs/non-bugs. We investigate the feasibility and relevance of automatically labeling issues with what we call “API-domains,” which are high-level categories of APIs. Therefore, we posit that the APIs used in the source code affected by an issue can be a proxy for the type of skills (e.g., DB, security, UI) needed to work on the issue. We ran a user study (n=74) to assess API-domain labels’ relevancy to potential contributors, leveraged the issues’ descriptions and the project history to build prediction models, and validated the predictions with contributors (n=20) of the projects. Our results show that (i) newcomers to the project consider API-domain labels useful in choosing tasks, (ii) labels can be predicted with a precision of 84% and a recall of 78.6% on average, (iii) the results of the predictions reached up to 71.3% in precision and 52.5% in recall when training with a project and testing in another (transfer learning), and (iv) project contributors consider most of the predictions helpful in identifying needed skills. These findings suggest our approach can be applied in practice to automatically label issues, assisting developers in finding tasks that better match their skills.");

        BibEntry firstArticle = new BibEntry(StandardEntryType.Article)
            .withField(StandardField.AUTHOR, "Steinmacher, Igor and Balali, Sogol and Trinkenreich, Bianca and Guizani, Mariam and Izquierdo-Cortazar, Daniel and Cuevas Zambrano, Griselda G. and Gerosa, Marco Aurelio and Sarma, Anita")
            .withField(StandardField.DATE, "2021-09-09")
            .withField(StandardField.DOI, "10.1186/s13174-021-00140-z")
            .withField(StandardField.ISSN, "1867-4828")
            .withField(StandardField.JOURNAL, "Journal of Internet Services and Applications")
            .withField(StandardField.MONTH, "#sep#")
            .withField(StandardField.PAGES, "1--33")
            .withField(StandardField.NUMBER, "1")
            .withField(StandardField.VOLUME, "12")
            .withField(StandardField.PUBLISHER, "Springer")
            .withField(StandardField.TITLE, "Being a Mentor in open source projects")
            .withField(StandardField.YEAR, "2021")
            .withField(StandardField.FILE, ":https\\://www.biomedcentral.com/openurl/pdf?id=doi\\:10.1186/s13174-021-00140-z:PDF")
            .withField(StandardField.ABSTRACT, "Mentoring is a well-known way to help newcomers to Open Source Software (OSS) projects overcome initial contribution barriers. Through mentoring, newcomers learn to acquire essential technical, social, and organizational skills. Despite the importance of OSS mentors, they are understudied in the literature. Understanding who OSS project mentors are, the challenges they face, and the strategies they use can help OSS projects better support mentors’ work. In this paper, we employ a two-stage study to comprehensively investigate mentors in OSS. First, we identify the characteristics of mentors in the Apache Software Foundation, a large OSS community, using an online survey. We found that less experienced volunteer contributors are less likely to take on the mentorship role. Second, through interviews with OSS mentors (n=18), we identify the challenges that mentors face and how they mitigate them. In total, we identified 25 general mentorship challenges and 7 sub-categories of challenges regarding task recommendation. We also identified 13 strategies to overcome the challenges related to task recommendation. Our results provide insights for OSS communities, formal mentorship programs, and tool builders who design automated support for task assignment and internship.");

        BibEntry secondArticle = new BibEntry(StandardEntryType.Article)
                .withField(StandardField.AUTHOR, "Steinmacher, Igor and Gerosa, Marco and Conte, Tayana U. and Redmiles, David F.")
                .withField(StandardField.DATE, "2019-04-15")
                .withField(StandardField.DOI, "10.1007/s10606-018-9335-z")
                .withField(StandardField.ISSN, "0925-9724")
                .withField(StandardField.JOURNAL, "Computer Supported Cooperative Work (CSCW)")
                .withField(StandardField.MONTH, "#apr#")
                .withField(StandardField.PAGES, "247--290")
                .withField(StandardField.NUMBER, "1-2")
                .withField(StandardField.VOLUME, "28")
                .withField(StandardField.PUBLISHER, "Springer")
                .withField(StandardField.TITLE, "Overcoming Social Barriers When Contributing to Open Source Software Projects")
                .withField(StandardField.YEAR, "2019")
                .withField(StandardField.FILE, ":http\\://link.springer.com/openurl/pdf?id=doi\\:10.1007/s10606-018-9335-z:PDF")
                .withField(StandardField.ABSTRACT, "An influx of newcomers is critical to the survival, long-term success, and continuity of many Open Source Software (OSS) community-based projects. However, newcomers face many barriers when making their first contribution, leading in many cases to dropouts. Due to the collaborative nature of community-based OSS projects, newcomers may be susceptible to social barriers, such as communication breakdowns and reception issues. In this article, we report a two-phase study aimed at better understanding social barriers faced by newcomers. In the first phase, we qualitatively analyzed the literature and data collected from practitioners to identify barriers that hinder newcomers’ first contribution. We designed a model composed of 58 barriers, including 13 social barriers. In the second phase, based on the barriers model, we developed FLOSScoach, a portal to support newcomers making their first contribution. We evaluated the portal in a diary-based study and found that the portal guided the newcomers and reduced the need for communication. Our results provide insights for communities that want to support newcomers and lay a foundation for building better onboarding tools. The contributions of this paper include identifying and gathering empirical evidence of social barriers faced by newcomers; understanding how social barriers can be reduced or avoided by using a portal that organizes proper information for newcomers (FLOSScoach); presenting guidelines for communities and newcomers on how to reduce or avoid social barriers; and identifying new streams of research.");

        BibEntry thirdArticle = new BibEntry(StandardEntryType.InCollection)
            .withField(StandardField.AUTHOR, "Serrano Alves, Luiz Philipe and Wiese, Igor Scaliante and Chaves, Ana Paula and Steinmacher, Igor")
            .withField(StandardField.BOOKTITLE, "Chatbot Research and Design")
            .withField(StandardField.DATE, "2022-01-01")
            .withField(StandardField.DOI, "10.1007/978-3-030-94890-0_6")
            .withField(StandardField.ISBN, "978-3-030-94889-4")
            .withField(StandardField.FILE, ":http\\://link.springer.com/openurl/pdf?id=doi\\:10.1007/978-3-030-94890-0_6:PDF")
            .withField(StandardField.MONTH, "#jan#")
            .withField(StandardField.PUBLISHER, "Springer")
            .withField(StandardField.YEAR, "2022")
            .withField(StandardField.TITLE, "How to Find My Task? Chatbot to Assist Newcomers in Choosing Tasks in OSS Projects")
            .withField(StandardField.ABSTRACT, "Open Source Software (OSS) is making a meteoric rise in the software industry since several big companies have entered this market. Unfortunately, newcomers enter these projects and usually lose interest in contributing because of several factors. This paper aims to reduce the problems users face when they walk their first steps into OSS projects: finding the appropriate task. This paper presents a chatbot that filters tasks to help newcomers choose a task that fits their skills. We performed a quantitative and a qualitative study comparing the chatbot with the current GitHub issue tracker interface, which uses labels to categorize and identify tasks. The results show that users perceived the chatbot as easier to use than the GitHub issue tracker. Additionally, users tend to interpret the use of chatbots as situational, helping mainly newcomers and inexperienced contributors.");

        BibEntry fourthArticle = new BibEntry(StandardEntryType.Article)
            .withField(StandardField.AUTHOR, "Calefato, Fabio and Gerosa, Marco Aurélio and Iaffaldano, Giuseppe and Lanubile, Filippo and Steinmacher, Igor")
            .withField(StandardField.DATE, "2022-03-19")
            .withField(StandardField.DOI, "10.1007/s10664-021-10012-6")
            .withField(StandardField.FILE, ":http\\://link.springer.com/openurl/pdf?id=doi\\:10.1007/s10664-021-10012-6:PDF")
            .withField(StandardField.ISSN, "1382-3256")
            .withField(StandardField.JOURNAL, "Empirical Software Engineering")
            .withField(StandardField.MONTH, "#mar#")
            .withField(StandardField.NUMBER, "3")
            .withField(StandardField.PAGES, "1--41")
            .withField(StandardField.PUBLISHER, "Springer")
            .withField(StandardField.TITLE, "Will you come back to contribute? Investigating the inactivity of OSS core developers in GitHub")
            .withField(StandardField.VOLUME, "27")
            .withField(StandardField.YEAR, "2022")
            .withField(StandardField.ABSTRACT, "Several Open-Source Software (OSS) projects depend on the continuity of their development communities to remain sustainable. Understanding how developers become inactive or why they take breaks can help communities prevent abandonment and incentivize developers to come back. In this paper, we propose a novel method to identify developers’ inactive periods by analyzing the individual rhythm of contributions to the projects. Using this method, we quantitatively analyze the inactivity of core developers in 18 OSS organizations hosted on GitHub. We also survey core developers to receive their feedback about the identified breaks and transitions. Our results show that our method was effective for identifying developers’ breaks. About 94% of the surveyed core developers agreed with our state model of inactivity; 71% and 79% of them acknowledged their breaks and state transition, respectively. We also show that all core developers take breaks (at least once) and about a half of them (~45%) have completely disengaged from a project for at least one year. We also analyzed the probability of transitions to/from inactivity and found that developers who pause their activity have a ~35 to ~55% chance to return to an active state; yet, if the break lasts for a year or longer, then the probability of resuming activities drops to ~21–26%, with a ~54% chance of complete disengagement. These results may support the creation of policies and mechanisms to make OSS community managers aware of breaks and potential project abandonment.");

        List<BibEntry> fetchedEntries = fetcher.performSearch("JabRef Social Barriers Steinmacher");
        assertEquals(List.of(articleSupportingIdentification, articleTagThatIssue, fourthArticle, thirdArticle, firstArticle, secondArticle), fetchedEntries);
    }

    @Test
    void springerJSONToBibtex() {
        String jsonString = """
                {\r
                            "identifier":"doi:10.1007/BF01201962",\r
                            "title":"Book reviews",\r
                            "publicationName":"World Journal of Microbiology & Biotechnology",\r
                            "issn":"1573-0972",\r
                            "isbn":"",\r
                            "doi":"10.1007/BF01201962",\r
                            "publisher":"Springer",\r
                            "publicationDate":"1992-09-01",\r
                            "volume":"8",\r
                            "number":"5",\r
                            "startingPage":"550",\r
                            "url":"http://dx.doi.org/10.1007/BF01201962","copyright":"©1992 Rapid Communications of Oxford Ltd."\r
                        }""";

        JSONObject jsonObject = new JSONObject(jsonString);
        BibEntry bibEntry = SpringerFetcher.parseSpringerJSONtoBibtex(jsonObject);
        assertEquals(Optional.of("1992"), bibEntry.getField(StandardField.YEAR));
        assertEquals(Optional.of("5"), bibEntry.getField(StandardField.NUMBER));
        assertEquals(Optional.of("#sep#"), bibEntry.getField(StandardField.MONTH));
        assertEquals(Optional.of("10.1007/BF01201962"), bibEntry.getField(StandardField.DOI));
        assertEquals(Optional.of("8"), bibEntry.getField(StandardField.VOLUME));
        assertEquals(Optional.of("Springer"), bibEntry.getField(StandardField.PUBLISHER));
        assertEquals(Optional.of("1992-09-01"), bibEntry.getField(StandardField.DATE));
    }

    @Test
    void searchByEmptyQueryFindsNothing() throws FetcherException {
        assertEquals(List.of(), fetcher.performSearch(""));
    }

    @Test
    @Disabled("Year search is currently broken, because the API returns mutliple years.")
    @Override
    public void supportsYearSearch() {
    }

    @Test
    @Disabled("Year range search is not natively supported by the API, but can be emulated by multiple single year searches.")
    @Override
    public void supportsYearRangeSearch() {
    }

    @Test
    @Disabled("401 as of 2024-08-18")
    @Override
    public void supportsAuthorSearch() {
    }

    @Test
    @Disabled("401 as of 2024-08-18")
    @Override
    public void supportsJournalSearch() {
    }

    @Test
    @Disabled("401 as of 2024-08-18")
    void supportsPhraseSearch() throws FetcherException {
        // Normal search should match due to Redmiles, Elissa M., phrase search on the other hand should not find it.
        BibEntry expected = new BibEntry(StandardEntryType.InCollection)
                .withField(StandardField.AUTHOR, "Booth, Kayla M. and Dosono, Bryan and Redmiles, Elissa M. and Morales, Miraida and Depew, Michael and Farzan, Rosta and Herman, Everett and Trahan, Keith and Tananis, Cindy")
                .withField(StandardField.DATE, "2018-01-01")
                .withField(StandardField.DOI, "10.1007/978-3-319-78105-1_75")
                .withField(StandardField.ISBN, "978-3-319-78104-4")
                .withField(StandardField.MONTH, "#jan#")
                .withField(StandardField.PUBLISHER, "Springer")
                .withField(StandardField.BOOKTITLE, "Transforming Digital Worlds")
                .withField(StandardField.TITLE, "Diversifying the Next Generation of Information Scientists: Six Years of Implementation and Outcomes for a Year-Long REU Program")
                .withField(StandardField.YEAR, "2018")
                .withField(StandardField.FILE, ":http\\://link.springer.com/openurl/pdf?id=doi\\:10.1007/978-3-319-78105-1_75:PDF")
                .withField(StandardField.ABSTRACT, "The iSchool Inclusion Institute (i3) is a Research Experience for Undergraduates (REU) program in the US designed to address underrepresentation in the information sciences. i3 is a year-long, cohort-based program that prepares undergraduate students for graduate school in information science and is rooted in a research and leadership development curriculum. Using data from six years of i3 cohorts, we present in this paper a qualitative and quantitative evaluation of the program in terms of student learning, research production, and graduate school enrollment. We find that students who participate in i3 report significant learning gains in information-science- and graduate-school-related areas and that 52% of i3 participants enroll in graduate school, over 2 $$\\times $$ × the national average. Based on these and additional results, we distill recommendations for future implementations of similar programs to address underrepresentation in information science.");

        List<BibEntry> resultPhrase = fetcher.performSearch("author:\"Redmiles David\"");
        List<BibEntry> result = fetcher.performSearch("author:Redmiles David");

        // Phrase search should be a subset of the normal search result.
        assertTrue(result.containsAll(resultPhrase));
        result.removeAll(resultPhrase);
        assertEquals(List.of(expected), result);
    }

    @Test
    @Disabled("401 as of 2024-08-18")
    void supportsBooleanANDSearch() throws FetcherException {
        List<BibEntry> resultJustByAuthor = fetcher.performSearch("author:\"Redmiles, David\"");
        List<BibEntry> result = fetcher.performSearch("author:\"Redmiles, David\" AND journal:\"Computer Supported Cooperative Work\"");

        assertTrue(resultJustByAuthor.containsAll(result));
        List<BibEntry> allEntriesFromCSCW = result.stream()
                                                  .filter(bibEntry -> "Computer Supported Cooperative Work (CSCW)"
                                                                              .equals(bibEntry.getField(StandardField.JOURNAL)
                                                                                              .orElse("")))
                                                  .toList();
        allEntriesFromCSCW.stream()
                          .map(bibEntry -> bibEntry.getField(StandardField.AUTHOR))
                          .filter(Optional::isPresent)
                          .map(Optional::get).forEach(authorField -> assertTrue(authorField.contains("Redmiles")));
    }

    @Override
    public SearchBasedFetcher getFetcher() {
        return fetcher;
    }

    @Override
    public List<String> getTestAuthors() {
        return List.of("Steinmacher, Igor", "Gerosa, Marco", "Conte, Tayana U.");
    }

    @Override
    public String getTestJournal() {
        return "Clinical Research in Cardiology";
    }

    @Override
    public PagedSearchBasedFetcher getPagedFetcher() {
        return fetcher;
    }
}
